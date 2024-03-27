package com.example.booking.controller;

import com.example.booking.AbstractTest;
import com.example.booking.StringTestUtils;
import com.example.booking.dto.RoomRequest;
import com.example.booking.model.Room;
import com.example.booking.repository.RoomRepository;
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

public class RoomControllerTest extends AbstractTest {

    @Autowired
    protected RoomRepository roomRepository;

    public static final long ID_ROOM_TO_TEST = 1;

    @Test
    @DisplayName("Test of get room by ID")
    @WithMockUser
    public void whenGetRoomById_thenReturnRoom() throws Exception {
        String actualResponse = mockMvc.perform(get("/api/room/{id}", ID_ROOM_TO_TEST))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/room_get_by_id.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Room creation test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenCreateRoom_thenReturnRoom() throws Exception {
        assertEquals(10, roomRepository.count());

        RoomRequest request = new RoomRequest();
        request.setName("New name");
        request.setDescription("New description");
        request.setNumber("100A");
        request.setPrice(2500);
        request.setMaxPeopleNumber(2);
        request.setHotelId(2);

        String actualResponse = mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/room_create.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("creationTime", "updateTime"));
        assertEquals(11, roomRepository.count());
    }

    @Test
    @DisplayName("Room update test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUpdateRoom_thenReturnRoom() throws Exception {
        Room roomToUpdate = roomRepository.findById(ID_ROOM_TO_TEST).orElseThrow();
        RoomRequest request = new RoomRequest();
        request.setName("New name");
        request.setDescription("New description");
        request.setNumber("100A");
        request.setPrice(2500);
        request.setMaxPeopleNumber(3);
        request.setHotelId(2);

        String actualResponse = mockMvc.perform(put("/api/room/{id}", ID_ROOM_TO_TEST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/room_update.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("updateTime"));
    }

    @Test
    @DisplayName("Room delete test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenDeleteRoom_thenReturnNoContent() throws Exception {
        assertEquals(10, roomRepository.count());

        mockMvc.perform(delete("/api/room/{id}", ID_ROOM_TO_TEST))
                .andExpect(status().isNoContent());

        assertEquals(9, roomRepository.count());
    }

    @Test
    @DisplayName("Rooms filter test")
    @WithMockUser
    public void whenFilterRooms_thenReturnFilteredList() throws Exception {
        String actualResponse = mockMvc.perform(get("/api/room/filter")
                        .param("name", "%room%")
                        .param("minPrice", "2000")
                        .param("maxPrice", "4000")
                        .param("maxPeopleNumber", "2")
                        .param("arrivalDate", "2024-04-03")
                        .param("departDate", "2024-04-06")
                        .param("hotelId", "1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/room_filter.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }
}
