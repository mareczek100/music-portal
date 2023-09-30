package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
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
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDtoTestData;
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
import java.util.List;

import static mareczek100.musiccontests.api.controller.TeacherController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TeacherController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class TeacherControllerTest {

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
        Assertions.assertNotNull(studentService);
        Assertions.assertNotNull(studentDtoMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void teacherHomePage() throws Exception {
        //given
        String welcomeMessage = "Witaj nauczycielu!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(TEACHER_MAIN_PAGE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }

    @Test
    void teacherSearchCompetitionsByInstrument() throws Exception {
        //given
        String teacherEmail = TeacherDtoTestData.teacherDtoSaved1().email();
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_COMPETITIONS_INSTRUMENT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_competition_instrument"))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andReturn();
    }

    @Test
    void teacherSearchCompetitionsByFilters() throws Exception {
        //given
        String teacherEmail = TeacherDtoTestData.teacherDtoSaved1().email();
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_COMPETITIONS_FILTERS)
                        .contentType(MediaType.TEXT_HTML)
                .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_competition_filters"))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("competitionDto", CompetitionWithLocationDto.builder().build()))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andReturn();
    }

    @Test
    void teacherSearchAllCompetitionsWithSortingAndPaginationToPutStudent() throws Exception {
        //given
        int currentPage = 1;
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();

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
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail))
                .thenReturn(teacher);
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_COMPETITIONS_ALL,
                                currentPage)
                        .contentType(MediaType.TEXT_HTML)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_competition_all"))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andExpect(model().attribute("currentPage", currentPage))
                .andExpect(model().attribute("allPages", allPages))
                .andExpect(model().attribute("allCompetitions", allCompetitions))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("competitionDTOs", competitionDtoList))
                .andReturn();
    }

    @Test
    void teacherSelectStudentToCompetitionByFilters() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();
        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_SELECT)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_select"))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andReturn();
    }
    @Test
    void teacherSelectStudentToCompetitionByInstrument() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1()
                .withCompetitionOnline(null).withCompetitionPrimaryDegree(null)
                .withCompetitionSecondaryDegree(null).withAddressCity(null);
        List<CompetitionWithLocationDto> competitionDTOs = CompetitionDtoTestData.competitionDtoList();
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();
        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_SELECT)
                        .contentType(MediaType.TEXT_HTML)
                        .flashAttr("competitionDto", competitionDto)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_select"))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andReturn();
    }

    @Test
    void teacherStudentAnnounceToCompetition() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        Student student = StudentDomainTestData.studentSaved1().withStudentId("f9c8d496-e1d4-418b-8bbf-9e8935a1a011");
        String studentId = student.studentId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        ApplicationForm applicationFormSaved = ApplicationFormDomainTestData.applicationFormToSave1();
        ApplicationFormDto applicationFormDto = ApplicationFormDtoTestData.applicationFormDtoSaved1();
        ClassLevel classLevel = applicationFormDto.classLevel();
        String performancePieces = applicationFormDto.performancePieces();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        Mockito.when(applicationFormService.insertApplicationForm(Mockito.any(ApplicationForm.class)))
                .thenReturn(applicationFormSaved);
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormSaved))
                .thenReturn(applicationFormDto);


        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_ANNOUNCE)
                        .contentType(MediaType.TEXT_HTML)
                        .param("studentId", studentId)
                        .param("competitionId", competitionId)
                        .param("classLevel", classLevel.name())
                        .param("performancePieces", performancePieces)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_announce"))
                .andExpect(model().attribute("applicationFormDto", applicationFormDto))
                .andReturn();
    }

    @Test
    void teacherCancelStudent() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_CANCEL)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_cancel"))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void teacherCancelStudentConfirm() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_CANCEL_SELECT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionDateFrom", competitionDateFrom.toString())
                        .param("competitionDateTo", competitionDateTo.toString())
                        .param("competitionCity", addressCity)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_cancel_select"))
                .andExpect(model().attribute("teacherEmail", teacherEmail))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void testTeacherCancelStudentConfirm() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();

        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();

        //when
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
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
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_CANCEL_CONFIRM)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_cancel_confirm"))
                .andExpect(model().attribute("applicationDeleted", false))
                .andExpect(model().attribute("studentDTOs", studentDtoList))
                .andExpect(model().attribute("competitionId", competitionId))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andReturn();
    }

    @Test
    void teacherCancelStudentConfirmDone() throws Exception {
        //given
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        StudentDto studentDto = StudentDtoTestData.studentDtoSaved1();
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();

        //when
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(student)).thenReturn(studentDto);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competition)).thenReturn(competitionDto);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                TEACHER_MAIN_PAGE + TEACHER_STUDENT_CANCEL_CONFIRM)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId)
                        .param("studentId", studentId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_student_cancel_confirm"))
                .andExpect(model().attribute("applicationDeleted", true))
                .andExpect(model().attribute("studentDto", studentDto))
                .andExpect(model().attribute("competitionDto", competitionDto))
                .andReturn();
    }

    @Test
    void teacherCheckCompetitionResultHomePage() throws Exception {
        //given
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();
        List<String> competitionCityList = CompetitionDomainTestData.competitionCityList().stream().distinct().toList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_MAIN_PAGE + TEACHER_RESULT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_result"))
                .andExpect(model().attribute("cityDTOs", competitionCityList))
                .andReturn();
    }

    @Test
    void teacherCheckCompetitionResultByFilters() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolToSaveDto1();
        LocalDate competitionDateTo = competitionDto.competitionBeginning().plusDays(1).toLocalDate();
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
                                TEACHER_MAIN_PAGE + TEACHER_RESULT_SEARCH)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionFrom", competitionDateFrom.toString())
                        .param("competitionTo", competitionDateTo.toString())
                        .param("competitionCity", addressCity))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_result_search"))
                .andExpect(model().attribute("competitionDTOs", competitionDTOs))
                .andReturn();
    }

    @Test
    void teacherCheckCompetitionResult() throws Exception {
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
                                TEACHER_MAIN_PAGE + TEACHER_RESULT_SHOW)
                        .contentType(MediaType.TEXT_HTML)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("teacher/teacher_result_show"))
                .andExpect(model().attribute("resultDTOs", competitionResultDtoList))
                .andExpect(model().attribute("competitionName", competition.name()))
                .andReturn();
    }
}