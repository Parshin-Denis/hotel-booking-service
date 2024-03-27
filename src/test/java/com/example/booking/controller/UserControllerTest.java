package com.example.booking.controller;

import com.example.booking.AbstractTest;
import com.example.booking.StringTestUtils;
import com.example.booking.dto.UserRequest;
import com.example.booking.model.RoleType;
import com.example.booking.repository.UserRepository;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractTest {

    @Autowired
    protected UserRepository userRepository;

    public static final long ID_USER_TO_TEST = 1;

    @Test
    @DisplayName("User creation test")
    public void whenCreateUser_thenReturnNewUser() throws Exception {
        assertEquals(3, userRepository.count());
        UserRequest request = new UserRequest();
        request.setName("newUser");
        request.setEmail("newuser@email.com");
        request.setPassword("12345");
        request.setRole(RoleType.ROLE_USER);

        String actualResponse = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/user_create.json");

        assertEquals(4, userRepository.count());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("password", "creationTime", "updateTime"));
    }

    @Test
    @DisplayName("Test of get user by ID")
    @WithMockUser
    public void whenGetUserById_thenReturnUser() throws Exception {
        String actualResponse = mockMvc.perform(get("/api/user/{id}", ID_USER_TO_TEST))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/user_get_by_id.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }



    @Test
    @DisplayName("User update test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUpdateUser_thenReturnUpdatedUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("updatedUser");
        request.setPassword("12345");
        request.setEmail("new@email.com");
        request.setRole(RoleType.ROLE_USER);

        String actualResponse = mockMvc.perform(put("/api/user/{id}", ID_USER_TO_TEST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/user_update.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("password", "updateTime"));
    }

    @Test
    @DisplayName("User delete test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenDeleteUser_thenReturnNoContent() throws Exception {
        assertEquals(3, userRepository.count());
        mockMvc.perform(delete("/api/user/{id}", ID_USER_TO_TEST))
                .andExpect(status().isNoContent());
        assertEquals(2, userRepository.count());
    }
}
