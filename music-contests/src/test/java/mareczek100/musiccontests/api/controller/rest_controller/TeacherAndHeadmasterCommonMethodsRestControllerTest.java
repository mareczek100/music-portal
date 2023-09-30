package mareczek100.musiccontests.api.controller.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.AllUsersRestUtils;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.TeacherRestUtils;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import mareczek100.musiccontests.api.dto.mapper.ApplicationFormDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionResultDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDomainTestData;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mareczek100.musiccontests.api.controller.rest_controller.TeacherRestController.TEACHER_REST_MAIN_PAGE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = TeacherRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class TeacherAndHeadmasterCommonMethodsRestControllerTest implements ControllerRestSupport {

    @MockBean
    private final CompetitionService competitionService;
    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final TeacherService teacherService;
    @MockBean
    private final StudentService studentService;
    @MockBean
    private final StudentDtoMapper studentDtoMapper;
    @MockBean
    private final ApplicationFormService applicationFormService;
    @MockBean
    private final ApplicationFormDtoMapper applicationFormDtoMapper;
    @MockBean
    private final CompetitionResultService competitionResultService;
    @MockBean
    private final CompetitionResultDtoMapper competitionResultDtoMapper;
    @MockBean
    private final AllUsersRestUtils allUsersRestUtils;
    @MockBean
    private final TeacherRestUtils teacherRestUtils;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(competitionDtoMapper);
        Assertions.assertNotNull(teacherService);
        Assertions.assertNotNull(studentService);
        Assertions.assertNotNull(studentDtoMapper);
        Assertions.assertNotNull(applicationFormService);
        Assertions.assertNotNull(applicationFormDtoMapper);
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionResultDtoMapper);
        Assertions.assertNotNull(allUsersRestUtils);
        Assertions.assertNotNull(teacherRestUtils);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void findAllClassLevels() throws Exception {
        //given
        List<ClassLevel> classLevelList = Arrays.stream(ClassLevel.values()).toList();
        ClassLevels classLevels = ClassLevels.builder().classLevelList(classLevelList).build();

        //when
        Mockito.when(teacherRestUtils.findAllClassLevels()).thenReturn(classLevels);

        String classLevelsJson = objectMapper.writeValueAsString(classLevels);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_CLASS_LEVELS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(classLevelsJson);
    }

    @Test
    void findFinishedCompetitionsByFilters() throws Exception {
        //given
        CompetitionWithLocationDto competitionFinishedDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1().withCompetitionFinished(true);
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competitionDto -> competitionDto.withCompetitionFinished(true)).toList();
        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDtoList).build();

        LocalDate competitionDateFrom = competitionFinishedDto.competitionBeginning().minusDays(2L).toLocalDate();
        LocalDate competitionDateTo = competitionFinishedDto.competitionBeginning().plusDays(2L).toLocalDate();
        String competitionCity = competitionFinishedDto.addressCity();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(teacherRestUtils.findFinishedCompetitionsByFilters(
                Mockito.anyString(), Mockito.anyString(),
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
                                TEACHER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS_BY_FILTERS)
                        .param("competitionDateFrom", competitionDateFrom.toString())
                        .param("competitionDateTo", competitionDateTo.toString())
                        .param("competitionCity", competitionCity)
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
    void findAllFinishedCompetitions() throws Exception {
        //given
        CompetitionWithLocationDto competitionFinishedDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1().withCompetitionFinished(true);
        List<Competition> competitionList = CompetitionDomainTestData.competitionList().stream()
                .map(competition -> competition.withFinished(true)).toList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList().stream()
                .map(competitionDto -> competitionDto.withCompetitionFinished(true)).toList();
        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDtoList).build();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(teacherRestUtils.findAllFinishedCompetitions()).thenReturn(competitionsDto);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        String competitionsDtoJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS)
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
    void findAllTeacherStudents() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();
        StudentsDto studentsDto = StudentDtoTestData.studentsDtoList();

        //when
        Mockito.when(teacherRestUtils.findAllTeacherStudents(teacherEmail))
                .thenReturn(ResponseEntity.ok(studentsDto));
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

        String studentsDtoJson = objectMapper.writeValueAsString(studentsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_ALL_TEACHER_STUDENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("teacherEmail", teacherEmail))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(studentsDtoJson);
    }

    @Test
    void announceStudentToCompetition() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();
        ApplicationFormDto applicationFormDtoToSave = ApplicationFormDtoTestData.applicationFormDtoToSave1();
        ApplicationFormDto applicationFormDtoSaved = ApplicationFormDtoTestData.applicationFormDtoSaved1();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        String classLevel = applicationFormDtoToSave.classLevel().getClassLevel();
        String performancePieces = applicationFormDtoToSave.performancePieces();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("teacherEmail", teacherEmail);
        multiValueMap.add("studentId", studentId);
        multiValueMap.add("competitionId", competitionId);
        multiValueMap.add("classLevel", classLevel);
        multiValueMap.add("performancePieces", performancePieces);

        //when
        Mockito.when(teacherRestUtils.announceStudentToCompetition(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(applicationFormDtoSaved));
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);

        String applicationFormDtoSavedJson = objectMapper.writeValueAsString(applicationFormDtoSaved);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_TO_COMPETITION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(applicationFormDtoSavedJson);
    }

    @Test
    void findTeacherApplicationsToCompetition() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();
        ApplicationFormDto applicationFormDtoToSave = ApplicationFormDtoTestData.applicationFormDtoToSave1();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        List<ApplicationFormDto> applicationFormDtoList = ApplicationFormDtoTestData.applicationFormDtoList();
        ApplicationFormsDto applicationFormsDto = ApplicationFormDtoTestData.applicationFormsDtoList();


        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("teacherEmail", teacherEmail);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(teacherRestUtils.findTeacherApplicationsToCompetition(
                Mockito.anyString(), Mockito.anyString())).thenReturn(applicationFormsDto);
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormList.get(0)))
                .thenReturn(applicationFormDtoList.get(0));
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormList.get(1)))
                .thenReturn(applicationFormDtoList.get(1));
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormList.get(2)))
                .thenReturn(applicationFormDtoList.get(2));
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormList.get(3)))
                .thenReturn(applicationFormDtoList.get(3));
        Mockito.when(applicationFormDtoMapper.mapFromDomainToDto(applicationFormList.get(4)))
                .thenReturn(applicationFormDtoList.get(4));

        String applicationFormsDtoJson = objectMapper.writeValueAsString(applicationFormsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + FIND_TEACHER_APPLICATIONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(jsonPath("$.applicationFormDtoList[0].performancePieces",
                        Matchers.is(applicationFormDtoToSave.performancePieces())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(applicationFormsDtoJson);
    }

    @Test
    void announceStudentToCompetitionCancel() throws Exception {
        //given
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("studentId", studentId);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(teacherRestUtils.announceStudentToCompetitionCancel(
                Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity.noContent().build());
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(applicationFormList);
        applicationFormService.deleteApplicationForm(Mockito.any(ApplicationForm.class));

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isNoContent())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }
    @Test
    void announceStudentToCompetitionCancelThrowExceptionIfStudentIsNotAnnouncedToCompetition() throws Exception {
        //given
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();

        String errorMessage = "Sorry, student [%s] [%s] isn't announced to competition [%s]!"
                .formatted(student.name(), student.surname(), competition.name());

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("studentId", studentId);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(teacherRestUtils.announceStudentToCompetitionCancel(
                Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException("Sorry, student [%s] [%s] isn't announced to competition [%s]!"
                        .formatted(student.name(), student.surname(), competition.name())));
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(Collections.emptyList());

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isBadRequest())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(errorMessage);
    }
    @Test
    void announceStudentToCompetitionCancelResponseProblemDetailMessageIfCompetitionStartsInLessThan2Hours() throws Exception {
        //given
        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();

        String problemDetailMessage = "Too late! It is less than 2 hours before competition starts";

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("studentId", studentId);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(teacherRestUtils.announceStudentToCompetitionCancel(
                        Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                        HttpStatus.REQUEST_TIMEOUT,
                        "Too late! It is less than 2 hours before competition starts")).build());
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(applicationFormService.findAllApplicationForms()).thenReturn(Collections.emptyList());

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isRequestTimeout())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(problemDetailMessage);
    }

    @Test
    void checkTeacherStudentsResults() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        Competition competition = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String competitionId = competition.competitionId();
        List<CompetitionResult> competitionResultList = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoList = CompetitionResultDtoTestData.competitionResultDtoListSaved();
        CompetitionResultsDto competitionResultsDto = CompetitionResultDtoTestData.competitionResultsDtoSaved();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("teacherEmail", teacherEmail);
        multiValueMap.add("competitionId", competitionId);

        //when
        Mockito.when(teacherRestUtils.checkTeacherStudentsResults(
                Mockito.anyString(), Mockito.anyString())).thenReturn(competitionResultsDto);
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
        Mockito.when(competitionResultService.findAllCompetitionResults()).thenReturn(competitionResultList);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(0)))
                .thenReturn(competitionResultDtoList.get(0));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(1)))
                .thenReturn(competitionResultDtoList.get(1));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultList.get(2)))
                .thenReturn(competitionResultDtoList.get(2));

        String competitionResultsDtoJson = objectMapper.writeValueAsString(competitionResultsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                TEACHER_REST_MAIN_PAGE + CHECK_RESULT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionResultsDtoJson);
    }
}