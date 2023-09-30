package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static mareczek100.musiccontests.api.controller.AdminController.ADMIN_MAIN_PAGE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AdminController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AdminControllerTest {

    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void adminHomePage() throws Exception {
        //given
        String welcomeMessage = "Panel administratora!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_MAIN_PAGE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("admin"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }
}