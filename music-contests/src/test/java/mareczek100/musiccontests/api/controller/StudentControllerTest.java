package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.InstrumentDto;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionResultDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.InstrumentDtoMapper;
import mareczek100.musiccontests.business.CompetitionResultService;
import mareczek100.musiccontests.business.CompetitionService;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static mareczek100.musiccontests.api.controller.StudentController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = StudentController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StudentControllerTest {

    @MockBean
    private final InstrumentApiService instrumentApiService;
    @MockBean
    private final InstrumentDtoMapper instrumentDtoMapper;
    @MockBean
    private final CompetitionService competitionService;
    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final CompetitionResultService competitionResultService;
    @MockBean
    private final CompetitionResultDtoMapper competitionResultDtoMapper;

    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionResultDtoMapper);
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(competitionDtoMapper);
        Assertions.assertNotNull(instrumentApiService);
        Assertions.assertNotNull(instrumentDtoMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void studentHomePage() throws Exception {
        //given
        String welcomeMessage = "Witaj uczniu!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_MAIN_PAGE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }

    @Test
    void studentSearchCompetitionsByInstrument() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(instrumentApiService.findAllInstruments()).thenReturn(instrumentList);
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(0)))
                .thenReturn(instrumentDtoList.get(0));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(1)))
                .thenReturn(instrumentDtoList.get(1));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(2)))
                .thenReturn(instrumentDtoList.get(2));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(3)))
                .thenReturn(instrumentDtoList.get(3));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(4)))
                .thenReturn(instrumentDtoList.get(4));
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_COMPETITION_INSTRUMENT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_competition_instrument"))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void studentSearchCompetitionsByFilters() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(instrumentApiService.findAllInstruments()).thenReturn(instrumentList);
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(0)))
                .thenReturn(instrumentDtoList.get(0));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(1)))
                .thenReturn(instrumentDtoList.get(1));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(2)))
                .thenReturn(instrumentDtoList.get(2));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(3)))
                .thenReturn(instrumentDtoList.get(3));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(4)))
                .thenReturn(instrumentDtoList.get(4));
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_COMPETITION_FILTERS)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_competition_filters"))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void studentShowAvailableCompetitionsByInstrument() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1()
                .withCompetitionOnline(null).withCompetitionPrimaryDegree(null)
                .withCompetitionSecondaryDegree(null).withAddressCity(null);
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();

        //when
        Mockito.when(competitionService.findCompetitionsByInstrument(Mockito.anyString()))
                .thenReturn(competitionList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDTOs.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDTOs.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDTOs.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_COMPETITION_SHOW)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_competition_show"))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void studentShowAvailableCompetitionsByFilters() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();

        //when
        Mockito.when(competitionService.findCompetitionsByFilters(
                competitionDto.competitionInstrument(), competitionDto.competitionOnline(),
                competitionDto.competitionPrimaryDegree(), competitionDto.competitionSecondaryDegree(),
                competitionDto.addressCity())).thenReturn(competitionList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDTOs.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDTOs.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDTOs.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_COMPETITION_SHOW)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_competition_show"))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void studentShowAllCompetitionsWithSortingAndPagination() throws Exception {
        //given
        int currentPage = 1;
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        Page<Competition> competitionListPageable = new PageImpl<>(competitionList);
        int allPages = 1;
        long allCompetitions = 3;

        //when
        Mockito.when(competitionService.findAllCompetitionsPageable(currentPage))
                .thenReturn(competitionListPageable);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_COMPETITION_SHOW_PAGES,
                                currentPage)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_competition_show_pages"))
                .andExpect(model().attribute("currentPage", currentPage))
                .andExpect(model().attribute("allPages", allPages))
                .andExpect(model().attribute("allCompetitions", allCompetitions))
                .andExpect(model().attribute("competitionDTOs", competitionDtoList))
                .andReturn();
    }

    @Test
    void studentCheckCompetitionResultsHomePage() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_RESULT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_result"))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void studentCheckCompetitionResultByFilters() throws Exception {
        //given
        String studentPesel = "13333333333";
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        LocalDate competitionDateTo = competitionDto.competitionEnd().plusDays(1).toLocalDate();
        LocalDate competitionDateFrom = competitionDto.competitionBeginning().minusDays(1).toLocalDate();
        String addressCity = competitionDto.addressCity();
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competition -> competition.withCompetitionFinished(true))
                .limit(1L).toList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults())
                .thenReturn(competitionResultList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDTOs.get(0));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                STUDENT_MAIN_PAGE + STUDENT_RESULT_SEARCH)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionFrom", competitionDateFrom.toString())
                        .param("competitionTo", competitionDateTo.toString())
                        .param("competitionCity", addressCity)
                        .param("studentPesel", studentPesel))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_result_search"))
                .andExpect(model().attribute("studentPesel", studentPesel))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void studentCheckCompetitionResult() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoList = CompetitionResultDtoTestData.competitionResultDtoListSaved();
        String studentPesel = "13333333333";

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(competitionResultList);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(0)))
                .thenReturn(competitionResultDtoList.get(0));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                STUDENT_MAIN_PAGE + STUDENT_RESULT_SHOW)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId)
                        .param("studentPesel", studentPesel))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("student/student_result_show"))
                .andExpect(model().attribute("resultDto", competitionResultDtoList.get(0)))
                .andExpect(model().attribute("competitionName", competition.name()))
                .andReturn();
    }
}