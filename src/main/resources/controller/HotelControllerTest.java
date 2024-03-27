package controller;

import com.example.booking.AbstractTest;
import com.example.booking.StringTestUtils;
import com.example.booking.dto.HotelRequest;
import com.example.booking.model.Hotel;
import com.example.booking.repository.HotelRepository;
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

public class HotelControllerTest extends AbstractTest {

    @Autowired
    protected HotelRepository hotelRepository;

    public static final long ID_HOTEL_TO_TEST = 1;

    @Test
    @DisplayName("Test of get all hotels")
    @WithMockUser
    public void whenGetAllHotels_thenReturnHotelsList() throws Exception {
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/hotel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_get_all.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Hotels filter test")
    @WithMockUser
    public void whenFilterHotels_thenReturnFilteredList() throws Exception {
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/hotel/filter")
                        .param("name", "%hotel%")
                        .param("headline", "%head%")
                        .param("city", "%city%")
                        .param("address", "%address%")
                        .param("distance", "1500")
                        .param("rating", "3.7")
                        .param("ratingsAmount", "40"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_filter.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test of get hotel by ID")
    @WithMockUser
    public void whenGetHotelById_thenReturnHotel() throws Exception {
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/hotel/{id}", ID_HOTEL_TO_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_get_by_id.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Hotel creation test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenCreateHotel_thenReturnHotel() throws Exception {
        Assertions.assertEquals(9, hotelRepository.count());

        HotelRequest request = new HotelRequest();
        request.setName("New name");
        request.setHeadline("New headline");
        request.setCity("New city");
        request.setAddress("New address");
        request.setDistance(1000);

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/hotel")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_create.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("creationTime", "updateTime"));
        Assertions.assertEquals(10, hotelRepository.count());
    }

    @Test
    @DisplayName("Hotel update test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUpdateHotel_thenReturnHotel() throws Exception {
        Hotel hotelToUpdate = hotelRepository.findById(ID_HOTEL_TO_TEST).orElseThrow();
        HotelRequest request = new HotelRequest();
        request.setName("New name");
        request.setHeadline("New headline");
        request.setCity("New city");
        request.setAddress("New address");
        request.setDistance(1000);

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.put("/api/hotel/{id}", ID_HOTEL_TO_TEST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_update.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("updateTime"));
    }

    @Test
    @DisplayName("Hotel rate test")
    @WithMockUser
    public void whenRateHotel_thenReturnHotel() throws Exception {
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/hotel/rate/{id}", ID_HOTEL_TO_TEST)
                        .param("mark", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/hotel_rate.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse,
                JsonAssert.whenIgnoringPaths("updateTime"));
    }

    @Test
    @DisplayName("Hotel delete test")
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "Admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenDeleteHotel_thenReturnNoContent() throws Exception {
        Assertions.assertEquals(9, hotelRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/hotel/{id}", ID_HOTEL_TO_TEST))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertEquals(8, hotelRepository.count());
    }
}
