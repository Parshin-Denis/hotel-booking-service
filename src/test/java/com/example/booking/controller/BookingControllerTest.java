package com.example.booking.controller;

import com.example.booking.AbstractTest;
import com.example.booking.StringTestUtils;
import com.example.booking.dto.BookingRequest;
import com.example.booking.repository.BookingRepository;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BookingControllerTest extends AbstractTest {

    @Autowired
    protected BookingRepository bookingRepository;

    @Test
    @DisplayName("Room booking test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "User1",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenBookRoom_thenReturnBooking() throws Exception {
        assertEquals(10, bookingRepository.count());
        BookingRequest request = new BookingRequest();
        request.setRoomId(5);
        request.setArrivalDate(LocalDate.of(2024, 4,15));
        request.setDepartDate(LocalDate.of(2024,4,18));


        String actualResponse = mockMvc.perform(post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/booking_create.json");

        assertEquals(11, bookingRepository.count());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("creationTime", "updateTime"));
    }

    @Test
    @DisplayName("Test of get all bookings")
    @WithMockUser(roles = {"ADMIN"})
    public void whenGetAllBookings_thenReturnBookingList() throws Exception {
        String actualResponse = mockMvc.perform(get("/api/booking"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/booking_get_all.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

}
