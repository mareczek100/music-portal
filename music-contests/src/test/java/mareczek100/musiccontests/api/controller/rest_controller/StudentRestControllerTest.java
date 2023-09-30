package mareczek100.musiccontests.api.controller.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.AllUsersRestUtils;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionResultDtoMapper;
import mareczek100.musiccontests.business.CompetitionResultService;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static mareczek100.musiccontests.api.controller.rest_controller.StudentRestController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = StudentRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StudentRestControllerTest implements ControllerRestSupport {

    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final CompetitionResultService competitionResultService;
    @MockBean
    private final CompetitionResultDtoMapper competitionResultDtoMapper;
    @MockBean
    private final AllUsersRestUtils allUsersRestUtils;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionDtoMapper);
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionResultDtoMapper);
        Assertions.assertNotNull(allUsersRestUtils);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }
    @Test
    void findFinishedStudentCompetitionsByFilters() throws Exception {
        //given
        String studentPesel = "13333333333";
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        CompetitionWithLocationDto competitionFinishedDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1().withCompetitionFinished(true);
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true))
                .limit(1L).toList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competitionDto -> competitionDto.withCompetitionFinished(true))
                .limit(1L).toList();
        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDtoList).build();

        LocalDate competitionDateFrom = competitionFinishedDto.competitionBeginning().minusDays(2L).toLocalDate();
        LocalDate competitionDateTo = competitionFinishedDto.competitionBeginning().plusDays(2L).toLocalDate();
        String competitionCity = competitionFinishedDto.addressCity();

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(competitionResultList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_REST_MAIN_PAGE + FIND_STUDENT_COMPETITIONS_BY_FILTERS)
                        .param("competitionDateFrom", competitionDateFrom.toString())
                        .param("competitionDateTo", competitionDateTo.toString())
                        .param("competitionCity", competitionCity)
                        .param("studentPesel", studentPesel)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.competitionDtoList[0].competitionFinished",
                        Matchers.is(competitionFinishedDto.competitionFinished())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }
    @Test
    void findAllFinishedStudentCompetitions() throws Exception {
        //given
        String studentPesel = "13333333333";
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        CompetitionWithLocationDto competitionFinishedDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1().withCompetitionFinished(true);
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true))
                .limit(1L).toList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competitionDto -> competitionDto.withCompetitionFinished(true))
                .limit(1L).toList();
        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDtoList).build();

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(competitionResultList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_REST_MAIN_PAGE + FIND_STUDENT_COMPETITIONS)
                        .param("studentPesel", studentPesel)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.competitionDtoList[0].competitionFinished",
                        Matchers.is(competitionFinishedDto.competitionFinished())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }

    @Test
    void checkStudentResult() throws Exception {
        //given
        String studentPesel = "13333333333";
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoList = CompetitionResultDtoTestData.competitionResultDtoListSaved();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("studentPesel", studentPesel);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(competitionResultList);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(0)))
                .thenReturn(competitionResultDtoList.get(0));

        String competitionResultDtoJson = objectMapper.writeValueAsString(competitionResultDtoList.get(0));

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_REST_MAIN_PAGE + CHECK_RESULT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionResultDtoJson);
    }
    @Test
    void checkStudentResultResponseNoContentIfResultDoesntExists() throws Exception {
        //given
        String studentPesel = "13333333333";
        String competitionId = "wrongIdNumber";

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("studentPesel", studentPesel);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(Collections.emptyList());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_REST_MAIN_PAGE + CHECK_RESULT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}