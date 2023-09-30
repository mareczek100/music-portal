package mareczek100.instrumentstorage.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@WebMvcTest(controllers = ErrorRestController.class)
class ErrorRestControllerTest {

    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void error() throws Exception {
        //given, when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                        ErrorRestController.HOME)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains("This endpoint is empty.");
    }

    @Test
    void errorEndpoint() throws Exception {
        //given, when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                ErrorRestController.ERROR)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains("This endpoint doesn't exist.");
    }
}