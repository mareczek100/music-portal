package mareczek100.musiccontests.api.controller.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.AllUsersRestUtils;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.TeacherRestUtils;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.InstrumentDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CitiesDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.InstrumentsDto;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.InstrumentDtoMapper;
import mareczek100.musiccontests.business.CompetitionService;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import org.hamcrest.Matchers;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

import static mareczek100.musiccontests.api.controller.rest_controller.TeacherRestController.TEACHER_REST_MAIN_PAGE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TeacherRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AllUsersCommonMethodsRestControllerTest implements ControllerRestSupport {

    @MockBean
    private final CompetitionService competitionService;
    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final InstrumentApiService instrumentApiService;
    @MockBean
    private final InstrumentDtoMapper instrumentDtoMapper;
    @MockBean
    private final AllUsersRestUtils allUsersRestUtils;
    @MockBean
    private final TeacherRestUtils teacherRestUtils;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(instrumentApiService);
        Assertions.assertNotNull(instrumentDtoMapper);
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(competitionDtoMapper);
        Assertions.assertNotNull(allUsersRestUtils);
        Assertions.assertNotNull(teacherRestUtils);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void findAllAvailableInstruments() throws Exception {
        //given
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        InstrumentsDto instrumentsDto = InstrumentDtoTestData.instrumentsDto();

        //when
        Mockito.when(instrumentApiService.findAllInstruments()).thenReturn(instrumentList);
        Mockito.when(allUsersRestUtils.findAllAvailableInstruments()).thenReturn(instrumentsDto);
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

        String instrumentsDtoJson = objectMapper.writeValueAsString(instrumentsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_INSTRUMENTS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(instrumentsDtoJson);
    }

    @Test
    void findAllAvailableCompetitionCities() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        CitiesDto citiesDto = CompetitionDomainTestData.competitionCities();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(allUsersRestUtils.findAllAvailableCompetitionCities()).thenReturn(citiesDto);

        String citiesDtoJson = objectMapper.writeValueAsString(citiesDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_CITIES)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(citiesDtoJson);
    }

    @Test
    void findAllAvailableCompetitionsWorksCorrectly() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        CompetitionsDto competitionsDto = CompetitionDtoTestData.competitionsDtoList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(allUsersRestUtils.findAllAvailableCompetitions())
                .thenReturn(ResponseEntity.ok(competitionsDto));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }

    @Test
    void findAllAvailableCompetitionsResponseMessageIfNoCompetitions() throws Exception {
        //given
        List<Competition> competitionList = Collections.emptyList();
        String problemDetailMessage = "No competitions, at all!";

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(allUsersRestUtils.findAllAvailableCompetitions())
                .thenReturn(ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND, "No competitions, at all!")).build());

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(problemDetailMessage);
    }
    @Test
    void findAllAvailableCompetitionsWithSortingAndPagingWorksCorrectly() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        Page<Competition> competitionListPageable = new PageImpl<>(competitionList);
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        CompetitionsDto competitionsDto = CompetitionDtoTestData.competitionsDtoList();
        Integer currentPage = 1;

        //when
        Mockito.when(competitionService.findAllCompetitionsPageable(currentPage))
                .thenReturn(competitionListPageable);
        Mockito.when(allUsersRestUtils.findAllAvailableCompetitionsPageable(currentPage))
                .thenReturn(ResponseEntity.ok(competitionsDto));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));


        String competitionsDtoJson
                = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }
    @Test
    void findAllAvailableCompetitionsWithSortingAndPagingMessageIfPageDoesNotExists() throws Exception {
        //given
        List<Competition> competitionList = Collections.emptyList();
        Page<Competition> competitionListPageable = new PageImpl<>(competitionList);
        Integer currentPage = 4;
        String problemDetailMessage = "No more competitions, page doesn't exist!";

        //when
        Mockito.when(competitionService.findAllCompetitionsPageable(currentPage))
                .thenReturn(competitionListPageable);
        Mockito.when(allUsersRestUtils.findAllAvailableCompetitionsPageable(currentPage))
                .thenReturn(ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND, "No more competitions, page doesn't exist!")).build());

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(problemDetailMessage);
    }

    @Test
    void findAvailableCompetitionsByFilters() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        CompetitionsDto competitionsDto = CompetitionDtoTestData.competitionsDtoList();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("competitionInstrument", competitionDto.competitionInstrument());
        multiValueMap.add("competitionOnline", competitionDto.competitionOnline().toString());
        multiValueMap.add("competitionPrimaryDegree", competitionDto.competitionPrimaryDegree().toString());
        multiValueMap.add("competitionSecondaryDegree", competitionDto.competitionSecondaryDegree().toString());
        multiValueMap.add("competitionCity", competitionDto.addressCity());

        //when
        Mockito.when(competitionService.findCompetitionsByFilters(
                Mockito.anyString(), Mockito.anyBoolean(),
                Mockito.anyBoolean(), Mockito.anyBoolean(),
                Mockito.anyString())).thenReturn(competitionList);
        Mockito.when(allUsersRestUtils.findAvailableCompetitionsByFilters(
                Mockito.anyString(), Mockito.anyBoolean(),
                Mockito.anyBoolean(), Mockito.anyBoolean(),
                Mockito.anyString())).thenReturn(competitionsDto);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_FILTERS)
                        .params(multiValueMap)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.competitionDtoList[0].competitionName",
                        Matchers.is(competitionDto.competitionName())))
                .andExpect(jsonPath("$.competitionDtoList[0].competitionPrimaryDegree",
                        Matchers.is(competitionDto.competitionPrimaryDegree())))
                .andExpect(jsonPath("$.competitionDtoList[0].addressCity",
                        Matchers.is(competitionDto.addressCity())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }

    @Test
    void findAllAvailableCompetitionsByInstrument() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        CompetitionsDto competitionsDto = CompetitionDtoTestData.competitionsDtoList();
        String competitionInstrument = competitionDto.competitionInstrument();

        //when
        Mockito.when(competitionService.findCompetitionsByInstrument(Mockito.anyString()))
                .thenReturn(competitionList);
        Mockito.when(allUsersRestUtils.findAvailableCompetitionsByInstrument(Mockito.anyString()))
                .thenReturn(competitionsDto);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT)
                        .param("competitionInstrument", competitionInstrument)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.competitionDtoList[0].competitionInstrument",
                        Matchers.is(competitionDto.competitionInstrument())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoJson);
    }

}