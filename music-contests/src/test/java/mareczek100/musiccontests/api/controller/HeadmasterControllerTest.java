package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.mapper.*;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDomainTestData;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mareczek100.musiccontests.api.controller.HeadmasterController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = HeadmasterController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class HeadmasterControllerTest {

    @MockBean
    private final InstrumentApiService instrumentApiService;
    @MockBean
    private final InstrumentDtoMapper instrumentDtoMapper;
    @MockBean
    private final ApplicationFormService applicationFormService;
    @MockBean
    private final ApplicationFormDtoMapper applicationFormDtoMapper;
    @MockBean
    private final CompetitionService competitionService;
    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final CompetitionResultService competitionResultService;
    @MockBean
    private final CompetitionResultDtoMapper competitionResultDtoMapper;
    @MockBean
    private final TeacherService teacherService;
    @MockBean
    private final TeacherDtoMapper teacherDtoMapper;
    @MockBean
    private final HeadmasterService headmasterService;
    @MockBean
    private final HeadmasterDtoMapper headmasterDtoMapper;
    @MockBean
    private final MusicSchoolService musicSchoolService;
    @MockBean
    private final MusicSchoolDtoMapper musicSchoolDtoMapper;
    @MockBean
    private final StudentService studentService;
    @MockBean
    private final StudentDtoMapper studentDtoMapper;

    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionResultDtoMapper);
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(competitionDtoMapper);
        Assertions.assertNotNull(applicationFormService);
        Assertions.assertNotNull(applicationFormDtoMapper);
        Assertions.assertNotNull(instrumentApiService);
        Assertions.assertNotNull(instrumentDtoMapper);
        Assertions.assertNotNull(teacherService);
        Assertions.assertNotNull(teacherDtoMapper);
        Assertions.assertNotNull(headmasterService);
        Assertions.assertNotNull(headmasterDtoMapper);
        Assertions.assertNotNull(musicSchoolService);
        Assertions.assertNotNull(musicSchoolDtoMapper);
        Assertions.assertNotNull(studentService);
        Assertions.assertNotNull(studentDtoMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void headmasterHomePage() throws Exception {
        //given
        String welcomeMessage = "Witaj dyrektorze!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(HEADMASTER_MAIN_PAGE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }

    @Test
    void headmasterCreateCompetition() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();

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

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CREATE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_create"))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andReturn();
    }

    @Test
    void headmasterCreateCompetitionAtOrganizerSchool() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String competitionOrganizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionDtoSaved = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competitionAtOrganizerSchoolToSave
                = CompetitionDomainTestData.competitionAtOrganizerSchoolToSave1();
        Competition createdCompetition
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(competitionOrganizerEmail))
                .thenReturn(headmaster);
        Mockito.when(competitionDtoMapper.mapFromDtoToDomain(
                Mockito.any(CompetitionWithLocationDto.class)))
                .thenReturn(competitionAtOrganizerSchoolToSave);
        Mockito.when(competitionService.insertCompetition(competitionAtOrganizerSchoolToSave))
                .thenReturn(createdCompetition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(createdCompetition))
                .thenReturn(competitionDtoSaved);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CREATE)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionOrganizerEmail", competitionOrganizerEmail)
                        .param("competitionSchoolLocation", "true")
                        .flashAttr("competitionDto", competitionDtoSaved))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_create_done"))
                .andExpect(model().attribute("createdCompetitionDto", competitionDtoSaved))
                .andReturn();
    }
    @Test
    void headmasterCreateCompetitionAtOtherLocationProcess() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String competitionOrganizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CREATE)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionOrganizerEmail", competitionOrganizerEmail)
                        .param("competitionSchoolLocation", "false")
                        .flashAttr("competitionDto", competitionDto))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_create_location"))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andExpect(model().attribute("competitionOrganizerEmail", competitionOrganizerEmail))
                .andReturn();
    }

    @Test
    void headmasterCreateCompetitionAtOtherLocationCorrectly() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String competitionOrganizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionDtoSaved = CompetitionDtoTestData.competitionAtOtherLocationSavedDto1();
        CompetitionWithLocationDto competitionDtoToSave = CompetitionDtoTestData.competitionAtOtherLocationToSaveDto1();
        Competition competitionAtOtherLocationToSave
                = CompetitionDomainTestData.competitionAtOtherLocationToSave1();
        Competition createdCompetition
                = CompetitionDomainTestData.competitionAtOtherLocationSaved1();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(competitionOrganizerEmail))
                .thenReturn(headmaster);
        Mockito.when(competitionDtoMapper.mapFromDtoToDomain(
                Mockito.any(CompetitionWithLocationDto.class)))
                .thenReturn(competitionAtOtherLocationToSave);
        Mockito.when(competitionService.insertCompetition(Mockito.any(Competition.class)))
                .thenReturn(createdCompetition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(createdCompetition))
                .thenReturn(competitionDtoSaved);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CREATE_LOCATION)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionOrganizerEmail", competitionOrganizerEmail)
                        .flashAttr("competitionDto", competitionDtoToSave))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_create_done"))
                .andExpect(model().attribute("createdCompetitionDto", competitionDtoSaved))
                .andReturn();
    }

    @Test
    void headmasterStudentHomePage() throws Exception {
        //given
        String welcomeMessage = "Tutaj zgłosisz swojego ucznia do konkursu.";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_student"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }

    @Test
    void headmasterCreateTeacherRightsHomePage() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterTeacherEmail = headmaster.email();
        HeadmasterDto headmasterDto = HeadmasterDtoTestData.headmasterDtoSaved1();

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
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterTeacherEmail))
                .thenReturn(headmaster);
        Mockito.when(headmasterDtoMapper.mapFromDomainToDto(headmaster)).thenReturn(headmasterDto);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_TEACHER)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_teacher"))
                .andExpect(model().attribute("headmasterDto", headmasterDto))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("headmasterTeacherDto", TeacherDto.builder().build()))
                .andReturn();
    }

    @Test
    void headmasterCreateTeacherRights() throws Exception {
        //given
        MusicSchool musicSchool = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchool.musicSchoolId();
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        TeacherDto headmasterTeacherDtoToSave = HeadmasterDtoTestData.headmasterTeacherDtoToSave1();
        TeacherDto headmasterTeacherDtoSaved = HeadmasterDtoTestData.headmasterTeacherDtoSaved1();
        Teacher headmasterTeacherToSave = HeadmasterDomainTestData.headmasterTeacherToSave1();
        Teacher headmasterTeacherSaved = HeadmasterDomainTestData.headmasterTeacherSaved1();

        //when
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId)).thenReturn(musicSchool);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchool)).thenReturn(musicSchoolDto);
        Mockito.when(teacherDtoMapper.mapFromDtoToDomain(headmasterTeacherDtoToSave))
                .thenReturn(headmasterTeacherToSave);
        Mockito.when(teacherService.findAllTeachers()).thenReturn(Collections.emptyList());
        Mockito.when(teacherService.insertTeacher(headmasterTeacherToSave))
                .thenReturn(headmasterTeacherSaved);
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(headmasterTeacherSaved))
                .thenReturn(headmasterTeacherDtoSaved);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_TEACHER_RIGHTS)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("headmasterTeacherDto", headmasterTeacherDtoToSave)
                        .param("musicSchoolId", musicSchoolId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_teacher"))
                .andExpect(model().attribute("teacherCreated", true))
                .andExpect(model().attribute("headmasterTeacherDto", headmasterTeacherDtoSaved))
                .andReturn();
    }
    @Test
    void headmasterCreateTeacherRightsShowInfoMessageIfTeacherAccountAlreadyExists() throws Exception {
        //given
        MusicSchool musicSchool = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchool.musicSchoolId();
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        TeacherDto headmasterTeacherDtoToSave = HeadmasterDtoTestData.headmasterTeacherDtoToSave1();
        Teacher headmasterTeacherToSave = HeadmasterDomainTestData.headmasterTeacherToSave1();
        Teacher headmasterTeacherSaved = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String infoMessage = "Masz już przypisane uprawnienia nauczyciela, nie musisz tego robić ponownie!";

        //when
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId)).thenReturn(musicSchool);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchool)).thenReturn(musicSchoolDto);
        Mockito.when(teacherDtoMapper.mapFromDtoToDomain(headmasterTeacherDtoToSave))
                .thenReturn(headmasterTeacherToSave);
        Mockito.when(teacherService.findAllTeachers()).thenReturn(List.of(headmasterTeacherSaved));

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_TEACHER_RIGHTS)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("headmasterTeacherDto", headmasterTeacherDtoToSave)
                        .param("musicSchoolId", musicSchoolId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_teacher"))
                .andExpect(model().attribute("teacherExist", true))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(infoMessage);
    }

    @Test
    void headmasterSearchCompetitionByFiltersToPutStudent() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterTeacherEmail = headmaster.email();
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_FILTERS_SEARCH)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_search_filters"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void headmasterSearchCompetitionsByInstrumentToPutStudent() throws Exception {
        //given
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterTeacherEmail = headmaster.email();
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_INSTRUMENT_SEARCH)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_search_instrument"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }
    @Test
    void headmasterSearchAllCompetitionsWithSortingAndPaginationToPutStudent() throws Exception {
        //given
        int currentPage = 1;
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        TeacherDto headmasterTeacherDto = HeadmasterDtoTestData.headmasterTeacherDtoSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        List<Student> studentList = StudentDomainTestData.studentList().stream()
                .map(student -> student.withTeacher(headmasterTeacher))
                .toList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList().stream()
                .map(studentDto -> studentDto.withTeacher(headmasterTeacherDto))
                .toList();

        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        Page<Competition> competitionListPageable = new PageImpl<>(competitionList);
        int allPages = 1;
        long allCompetitions = 3;

        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        //when
        Mockito.when(competitionService.findAllCompetitionsPageable(currentPage))
                .thenReturn(competitionListPageable);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));
        Mockito.when(teacherService.findTeacherByEmail(headmasterTeacherEmail))
                .thenReturn(headmasterTeacher);
        Mockito.when(studentService.findAllStudents()).thenReturn(studentList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(0)))
                .thenReturn(studentDtoList.get(0));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(1)))
                .thenReturn(studentDtoList.get(1));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(2)))
                .thenReturn(studentDtoList.get(2));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(3)))
                .thenReturn(studentDtoList.get(3));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(4)))
                .thenReturn(studentDtoList.get(4));
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_ALL_SEARCH,
                                currentPage)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_search_all"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("currentPage", currentPage))
                .andExpect(model().attribute("allPages", allPages))
                .andExpect(model().attribute("allCompetitions", allCompetitions))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("competitionDTOs", competitionDtoList))
                .andReturn();
    }

    @Test
    void headmasterSelectStudentToCompetitionByFilters() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        TeacherDto headmasterTeacherDto = HeadmasterDtoTestData.headmasterTeacherDtoSaved1();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<Student> studentList = StudentDomainTestData.studentList().stream()
                .map(student -> student.withTeacher(headmasterTeacher)).toList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList().stream()
                .map(studentDto -> studentDto.withTeacher(headmasterTeacherDto)).toList();
        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(headmasterTeacherEmail)).thenReturn(headmasterTeacher);
        Mockito.when(studentService.findAllStudents()).thenReturn(studentList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(0)))
                .thenReturn(studentDtoList.get(0));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(1)))
                .thenReturn(studentDtoList.get(1));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(2)))
                .thenReturn(studentDtoList.get(2));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(3)))
                .thenReturn(studentDtoList.get(3));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(4)))
                .thenReturn(studentDtoList.get(4));
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_SELECT_STUDENT)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_select_student"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andReturn();
    }
    @Test
    void headmasterSelectStudentToCompetitionByInstrument() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        TeacherDto headmasterTeacherDto = HeadmasterDtoTestData.headmasterTeacherDtoSaved1();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1()
                .withCompetitionOnline(null).withCompetitionPrimaryDegree(null)
                .withCompetitionSecondaryDegree(null).withAddressCity(null);
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<Student> studentList = StudentDomainTestData.studentList().stream()
                .map(student -> student.withTeacher(headmasterTeacher)).toList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList().stream()
                .map(studentDto -> studentDto.withTeacher(headmasterTeacherDto)).toList();
        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(headmasterTeacherEmail)).thenReturn(headmasterTeacher);
        Mockito.when(studentService.findAllStudents()).thenReturn(studentList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(0)))
                .thenReturn(studentDtoList.get(0));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(1)))
                .thenReturn(studentDtoList.get(1));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(2)))
                .thenReturn(studentDtoList.get(2));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(3)))
                .thenReturn(studentDtoList.get(3));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(4)))
                .thenReturn(studentDtoList.get(4));
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_SELECT_STUDENT)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_select_student"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andReturn();
    }

    @Test
    void headmasterPutUpStudentToCompetition() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        Student student = StudentDomainTestData.studentSaved1().withStudentId("ff47dba3-84ad-437f-8df0-e51b1be41735");
        String studentId = student.studentId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        ApplicationForm applicationFormSaved = ApplicationFormDomainTestData.applicationFormToSave1();
        ApplicationFormDto applicationFormDto = ApplicationFormDtoTestData.applicationFormDtoSaved1();
        ClassLevel classLevel = applicationFormDto.classLevel();
        String performancePieces = applicationFormDto.performancePieces();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(teacherService.findTeacherByEmail(headmasterTeacherEmail)).thenReturn(headmasterTeacher);
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        Mockito.when(applicationFormService.insertApplicationForm(Mockito.any(ApplicationForm.class)))
                .thenReturn(applicationFormSaved);
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormSaved))
                .thenReturn(applicationFormDto);


        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT_PUT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("studentId", studentId)
                        .param("competitionId", competitionId)
                        .param("classLevel", classLevel.name())
                        .param("performancePieces", performancePieces)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_put_student"))
                .andExpect(model().attribute("applicationFormDto", applicationFormDto))
                .andReturn();
    }

    @Test
    void headmasterCancelStudent() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT_CANCEL)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_student_cancel"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void headmasterCancelStudentConfirm() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolToSaveDto1();
        LocalDate competitionDateTo = competitionDto.competitionBeginning().plusDays(1).toLocalDate();
        LocalDate competitionDateFrom = competitionDto.competitionBeginning().minusDays(1).toLocalDate();
        String addressCity = competitionDto.addressCity();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT_CANCEL_SELECT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionDateFrom", competitionDateFrom.toString())
                        .param("competitionDateTo", competitionDateTo.toString())
                        .param("competitionCity", addressCity)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_student_cancel_select"))
                .andExpect(model().attribute("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void testHeadmasterCancelStudentConfirm() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        String headmasterTeacherEmail = headmasterTeacher.email();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList().stream()
                .map(applicationForm -> applicationForm.withTeacher(headmasterTeacher)).toList();

        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(headmasterTeacherEmail)).thenReturn(headmasterTeacher);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competition)).thenReturn(competitionDto);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(0)))
                .thenReturn(studentDtoList.get(0));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(1)))
                .thenReturn(studentDtoList.get(1));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(2)))
                .thenReturn(studentDtoList.get(2));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(3)))
                .thenReturn(studentDtoList.get(3));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(4)))
                .thenReturn(studentDtoList.get(4));


        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT_CANCEL_CONFIRM)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId)
                        .param("headmasterTeacherEmail", headmasterTeacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_student_cancel_confirm"))
                .andExpect(model().attribute("applicationDeleted", false))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andExpect(model().attribute("competitionId", competitionId))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andReturn();
    }

    @Test
    void headmasterCancelStudentConfirmDone() throws Exception {
        //given
        Teacher headmasterTeacher = HeadmasterDomainTestData.headmasterTeacherSaved1();
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        StudentDto studentDto = StudentDtoTestData.studentDtoSaved1();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList().stream()
                .map(applicationForm -> applicationForm.withTeacher(headmasterTeacher)).toList();

        //when
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(student)).thenReturn(studentDto);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competition)).thenReturn(competitionDto);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_STUDENT_CANCEL_CONFIRM)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId)
                        .param("studentId", studentId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_student_cancel_confirm"))
                .andExpect(model().attribute("applicationDeleted", true))
                .andExpect(model().attribute("studentDto", studentDto))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andReturn();
    }

    @Test
    void headmasterAnnounceCompetitionResultHomePage() throws Exception {
        //given, when, then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_RESULT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_result"))
                .andExpect(model().attribute("noCompetitions", false))
                .andExpect(model().attribute("competitionDTOs", Collections.emptyList()))
                .andReturn();
    }

    @Test
    void headmasterPickCompetitionToAnnounceResult() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(competitionService.findCompetitionsByInstrument(Mockito.anyString()))
                .thenReturn(competitionList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDTOs.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDTOs.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDTOs.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_RESULT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("headmasterEmail", headmasterEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_result"))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void headmasterAnnounceCompetitionHomePage() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList()
                .stream().limit(3).toList();
        List<Student> studentList = StudentDomainTestData.studentList()
                .stream().limit(3).toList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList()
                .stream().limit(3).toList();
        CompetitionResultListDto competitionResultListDtoEmpty = CompetitionResultDtoTestData.competitionResultsDtoEmpty();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competition)).thenReturn(competitionDto);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(0)))
                .thenReturn(studentDtoList.get(0));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(1)))
                .thenReturn(studentDtoList.get(1));
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentList.get(2)))
                .thenReturn(studentDtoList.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_ANNOUNCE_RESULT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_result_announce"))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andExpect(model().attribute("resultListDto", competitionResultListDtoEmpty))
                .andReturn();
    }

    @Test
    void headmasterAnnounceCompetitionResult() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        List<CompetitionResult> competitionResultListSaved
                = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoListSaved
                = CompetitionResultDtoTestData.competitionResultDtoListSaved();
        CompetitionResultListDto competitionResultListDto
                = CompetitionResultDtoTestData.competitionResultsDtoToSave();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionService.updateCompetitionAfterResults(competition))
                .thenReturn(competition.withFinished(true));
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(competitionResultService.insertAllCompetitionResults(
                Mockito.anyList())).thenReturn(competitionResultListSaved);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(0)))
                .thenReturn(competitionResultDtoListSaved.get(0));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(1)))
                .thenReturn(competitionResultDtoListSaved.get(1));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(2)))
                .thenReturn(competitionResultDtoListSaved.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_ANNOUNCE_RESULT)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("resultListDto", competitionResultListDto)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_result_announce_done"))
                .andExpect(model().attribute("resultDTOs", competitionResultDtoListSaved))
                .andExpect(model().attribute("competitionName", competition.name()))
                .andReturn();
    }

    @Test
    void headmasterCheckCompetitionResultHomePage() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CHECK)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_check"))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();

    }

    @Test
    void headmasterCheckCompetitionResultsByFilters() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolToSaveDto1();
        LocalDate competitionDateTo = competitionDto.competitionEnd().plusDays(1).toLocalDate();
        LocalDate competitionDateFrom = competitionDto.competitionBeginning().minusDays(1).toLocalDate();
        String addressCity = competitionDto.addressCity();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competition -> competition.withCompetitionFinished(true)).toList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
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
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CHECK_RESULT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionFrom", competitionDateFrom.toString())
                        .param("competitionTo", competitionDateTo.toString())
                        .param("competitionCity", addressCity))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_check_filter"))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void headmasterCheckCompetitionResult() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoList = CompetitionResultDtoTestData.competitionResultDtoListSaved();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionResultService.findAllCompetitionResults())
                .thenReturn(competitionResultList);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(0)))
                .thenReturn(competitionResultDtoList.get(0));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(1)))
                .thenReturn(competitionResultDtoList.get(1));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(2)))
                .thenReturn(competitionResultDtoList.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_MAIN_PAGE + HEADMASTER_COMPETITION_CHECK_RESULT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("headmaster/headmaster_competition_check_result"))
                .andExpect(model().attribute("resultDTOs", competitionResultDtoList))
                .andExpect(model().attribute("competitionName", competition.name()))
                .andReturn();
    }
}