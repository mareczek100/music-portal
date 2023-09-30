package mareczek100.musiccontests.api.controller.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.AllUsersRestUtils;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.TeacherRestUtils;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import mareczek100.musiccontests.api.dto.mapper.*;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.business.dao.HeadmasterRepositoryDAO;
import mareczek100.musiccontests.business.dao.TeacherRepositoryDAO;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDomainTestData;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDtoTestData;
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

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.*;
import static mareczek100.musiccontests.api.dto.CompetitionWithLocationDto.DATE_TIME_FORMAT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = HeadmasterRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class HeadmasterRestControllerTest implements ControllerRestSupport {

    @MockBean
    private final CompetitionService competitionService;
    @MockBean
    private final CompetitionDtoMapper competitionDtoMapper;
    @MockBean
    private final TeacherService teacherService;
    @MockBean
    private final TeacherRepositoryDAO teacherRepositoryDAO;
    @MockBean
    private final TeacherDtoMapper teacherDtoMapper;
    @MockBean
    private final HeadmasterService headmasterService;
    @MockBean
    private final HeadmasterRepositoryDAO headmasterRepositoryDAO;
    @MockBean
    private final HeadmasterDtoMapper headmasterDtoMapper;
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
        Assertions.assertNotNull(teacherRepositoryDAO);
        Assertions.assertNotNull(teacherDtoMapper);
        Assertions.assertNotNull(headmasterService);
        Assertions.assertNotNull(headmasterRepositoryDAO);
        Assertions.assertNotNull(headmasterDtoMapper);
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
    void createCompetitionAtSchool() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String organizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolToSaveDto1();
        CompetitionWithLocationDto competitionSavedDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        Competition competitionToSave = CompetitionDomainTestData.competitionAtOrganizerSchoolToSave1();
        Competition competitionSaved = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("headmasterOrganizerEmail", organizerEmail);
        multiValueMap.add("competitionName", competitionToSaveDto.competitionName());
        multiValueMap.add("competitionInstrument", competitionToSaveDto.competitionInstrument());
        multiValueMap.add("competitionOnline", competitionToSaveDto.competitionOnline().toString());
        multiValueMap.add("competitionPrimaryDegree", competitionToSaveDto.competitionPrimaryDegree().toString());
        multiValueMap.add("competitionSecondaryDegree", competitionToSaveDto.competitionSecondaryDegree().toString());
        multiValueMap.add("competitionBeginning", competitionToSaveDto.competitionBeginning()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        multiValueMap.add("competitionEnd", competitionToSaveDto.competitionEnd()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        multiValueMap.add("competitionResultAnnouncement", competitionToSaveDto.competitionResultAnnouncement()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        multiValueMap.add("competitionApplicationDeadline", competitionToSaveDto.competitionApplicationDeadline()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        multiValueMap.add("competitionRequirementsDescription", competitionToSaveDto.competitionRequirementsDescription());

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(organizerEmail))
                .thenReturn(headmaster);
        Mockito.when(competitionDtoMapper.mapFromDtoToDomain(
                Mockito.any(CompetitionWithLocationDto.class)))
                .thenReturn(competitionToSave);
        Mockito.when(competitionService.insertCompetition(
                competitionToSave.withHeadmaster(headmaster)))
                .thenReturn(competitionSaved);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionSaved))
                .thenReturn(competitionSavedDto);
        String competitionSavedDtoJson = objectMapper.writeValueAsString(competitionSavedDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_REST_MAIN_PAGE + CREATE_COMPETITION_AT_SCHOOL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.competitionName",
                        Matchers.is(competitionToSaveDto.competitionName())))
                .andExpect(jsonPath("$.competitionPrimaryDegree",
                        Matchers.is(competitionToSaveDto.competitionPrimaryDegree())))
                .andExpect(jsonPath("$.competitionLocationName",
                        Matchers.is(competitionToSaveDto.competitionLocationName())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), competitionSavedDtoJson);
    }

    @Test
    void createCompetitionAtOtherLocation() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String organizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOtherLocationToSaveDto1();
        CompetitionWithLocationDto competitionSavedDto
                = CompetitionDtoTestData.competitionAtOtherLocationSavedDto1();
        Competition competitionToSave = CompetitionDomainTestData.competitionAtOtherLocationToSave1();
        Competition competitionSaved = CompetitionDomainTestData.competitionAtOtherLocationSaved1();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(organizerEmail))
                .thenReturn(headmaster);
        Mockito.when(competitionDtoMapper.mapFromDtoToDomain(
                        Mockito.any(CompetitionWithLocationDto.class)))
                .thenReturn(competitionToSave);
        Mockito.when(competitionService.insertCompetition(
                        competitionToSave.withHeadmaster(headmaster)))
                .thenReturn(competitionSaved);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionSaved))
                .thenReturn(competitionSavedDto);
        String competitionToSaveDtoJson = objectMapper.writeValueAsString(competitionToSaveDto);
        String competitionSavedDtoJson = objectMapper.writeValueAsString(competitionSavedDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_REST_MAIN_PAGE + CREATE_COMPETITION_AT_OTHER_LOCATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionToSaveDtoJson)
                        .param("headmasterOrganizerEmail", organizerEmail))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.competitionName",
                        Matchers.is(competitionToSaveDto.competitionName())))
                .andExpect(jsonPath("$.competitionPrimaryDegree",
                        Matchers.is(competitionToSaveDto.competitionPrimaryDegree())))
                .andExpect(jsonPath("$.competitionLocationName",
                        Matchers.is(competitionToSaveDto.competitionLocationName())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), competitionSavedDtoJson);
    }
    @Test
    void updateExistingCompetition() throws Exception {
        //given
        Headmaster newCompetitionOrganizer = HeadmasterDomainTestData.headmasterSaved1();
        String organizerEmail = newCompetitionOrganizer.email();
        CompetitionWithLocationRestDto newCompetitionToSaveDto = CompetitionDtoTestData.competitionToUpdateDto();
        CompetitionWithLocationDto competitionSavedDto
                = CompetitionDtoTestData.competitionAtOtherLocationSavedDto1();
        Competition competitionToSave = CompetitionDomainTestData.competitionAtOtherLocationToSave1();
        Competition competitionSaved = CompetitionDomainTestData.competitionAtOtherLocationSaved1();
        String existingCompetitionId = competitionSaved.competitionId();

        CompetitionWithLocationDto updatedCompetitionDto = CompetitionWithLocationDto.builder()
                .competitionName(newCompetitionToSaveDto.competitionName())
                .competitionPrimaryDegree(newCompetitionToSaveDto.competitionPrimaryDegree())
                .competitionLocationName(newCompetitionToSaveDto.competitionLocationName())
                .build();

        Competition updatedCompetition = competitionSaved
                .withName(updatedCompetitionDto.competitionName())
                .withPrimaryDegree(updatedCompetitionDto.competitionPrimaryDegree())
                .withCompetitionLocation(CompetitionLocation.builder()
                        .name(updatedCompetitionDto.competitionName())
                        .build());

        //when
        Mockito.when(competitionService.findCompetitionById(existingCompetitionId))
                .thenReturn(competitionSaved);
        Mockito.when(headmasterService.findHeadmasterByEmail(organizerEmail))
                .thenReturn(newCompetitionOrganizer);
        Mockito.when(competitionDtoMapper.mapFromDtoToDomain(
                        Mockito.any(CompetitionWithLocationDto.class)))
                .thenReturn(competitionToSave);
        Mockito.when(competitionService.insertCompetition(
                        competitionToSave.withHeadmaster(newCompetitionOrganizer)))
                .thenReturn(updatedCompetition);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(updatedCompetition))
                .thenReturn(updatedCompetitionDto);
        String competitionToSaveDtoJson = objectMapper.writeValueAsString(newCompetitionToSaveDto);
        String competitionSavedDtoJson = objectMapper.writeValueAsString(updatedCompetitionDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(
                                HEADMASTER_REST_MAIN_PAGE + UPDATE_COMPETITION_WITH_NEW_LOCATION,
                                existingCompetitionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionToSaveDtoJson)
                        .param("headmasterOrganizerEmail", organizerEmail))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.competitionName",
                        Matchers.is(newCompetitionToSaveDto.competitionName())))
                .andExpect(jsonPath("$.competitionPrimaryDegree",
                        Matchers.is(newCompetitionToSaveDto.competitionPrimaryDegree())))
                .andExpect(jsonPath("$.competitionLocationName",
                        Matchers.is(newCompetitionToSaveDto.competitionLocationName())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), competitionSavedDtoJson);
    }

    @Test
    void createHeadmasterTeacherRightsCorrectly() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        String instrumentName = InstrumentDtoTestData.instrumentDtoSaved1().name();
        TeacherDto headmasterTeacherDtoToSave1 = HeadmasterDtoTestData.headmasterTeacherDtoToSave1().withInstrument(instrumentName);
        TeacherDto headmasterTeacherDtoSaved1 = HeadmasterDtoTestData.headmasterTeacherDtoSaved1().withInstrument(instrumentName);
        Teacher headmasterTeacherToSave1 = HeadmasterDomainTestData.headmasterTeacherToSave1().withInstrument(instrumentName);
        Teacher headmasterTeacherSaved1 = HeadmasterDomainTestData.headmasterTeacherSaved1().withInstrument(instrumentName);

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterEmail)).thenReturn(headmaster);
        Mockito.when(teacherService.findAllTeachers()).thenReturn(Collections.emptyList());
        Mockito.when(headmasterRepositoryDAO.findHeadmasterByEmail(headmasterTeacherToSave1.email()))
                        .thenReturn(Optional.of(headmaster));
        Mockito.when(teacherRepositoryDAO.insertTeacher(headmasterTeacherToSave1))
                        .thenReturn(headmasterTeacherSaved1);
        Mockito.when(teacherService.insertTeacher(headmasterTeacherToSave1))
                .thenReturn(headmasterTeacherSaved1);
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(headmasterTeacherSaved1))
                .thenReturn(headmasterTeacherDtoSaved1);

        String headmasterTeacherSavedDtoJson = objectMapper.writeValueAsString(headmasterTeacherDtoSaved1);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_REST_MAIN_PAGE + CREATE_TEACHER_RIGHTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail)
                        .param("instrument", instrumentName))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        Matchers.is(headmasterTeacherDtoToSave1.name())))
                .andExpect(jsonPath("$.email",
                        Matchers.is(headmasterTeacherDtoToSave1.email())))
                .andExpect(jsonPath("$.pesel",
                        Matchers.is(headmasterTeacherDtoToSave1.pesel())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), headmasterTeacherSavedDtoJson);
    }
    @Test
    void createHeadmasterTeacherRightsAccountAlreadyExistsResponseProblemDetail() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        String instrumentName = InstrumentDtoTestData.instrumentDtoSaved1().name();
        Teacher headmasterTeacherSaved1 = HeadmasterDomainTestData.headmasterTeacherSaved1().withInstrument(instrumentName);

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterEmail))
                .thenReturn(headmaster);
        Mockito.when(teacherService.findAllTeachers())
                .thenReturn(List.of(headmasterTeacherSaved1));

        String statusDetailMessage = "Teacher with email [%s] already exist!".formatted(headmasterEmail);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_REST_MAIN_PAGE + CREATE_TEACHER_RIGHTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail)
                        .param("instrument", instrumentName))
                .andExpect(status().isConflict())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(
                mvcResult.getResponse().getContentAsString()).contains(statusDetailMessage);
    }

    @Test
    void findAllTeachers() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        List<Teacher> teacherList = TeacherDomainTestData.teacherList();
        List<TeacherDto> teacherDtoList = TeacherDtoTestData.teacherDtoList();
        TeachersDto teachersDto = TeacherDtoTestData.teachersDtoList();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterEmail)).thenReturn(headmaster);
        Mockito.when(teacherService.findAllTeachers()).thenReturn(teacherList);
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherList.get(0)))
                .thenReturn(teacherDtoList.get(0));
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherList.get(1)))
                .thenReturn(teacherDtoList.get(1));
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherList.get(2)))
                .thenReturn(teacherDtoList.get(2));
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherList.get(3)))
                .thenReturn(teacherDtoList.get(3));
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherList.get(4)))
                .thenReturn(teacherDtoList.get(4));

        String teacherDtoListJson = objectMapper.writeValueAsString(teachersDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(teacherDtoListJson);
    }

    @Test
    void findAllCompetitionStudentsExists() throws Exception {
        //given
        CompetitionWithLocationDto competitionDto = CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1();
        String competitionId = competitionDto.competitionId();

        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();
        List<Student> studentList = StudentDomainTestData.studentList();
        StudentsDto studentsDto = StudentDtoTestData.studentsDtoList();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();

        //when
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

        String studentsDtoListJson = objectMapper.writeValueAsString(studentsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITION_STUDENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("competitionId", competitionId))
                .andExpect(status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(studentsDtoListJson);
    }
    @Test
    void findAllCompetitionStudentsEmptyListResponseNotFoundStatus() throws Exception {
        //given
        String competitionId = "9d7af18c-f2c1-40ea-a8b8-f8f88bd65dfe";

        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        List<ApplicationForm> noStudentsApplicationFormList = applicationFormList.stream()
                .map(applicationForm -> applicationForm.withStudent(null))
                .toList();

        //when
        Mockito.when(applicationFormService.findAllApplicationForms())
                .thenReturn(noStudentsApplicationFormList);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(Mockito.any(Student.class)))
                .thenReturn(null);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITION_STUDENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("competitionId", competitionId))
                .andExpect(status().isNotFound())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    void findAllSchoolStudents() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        List<Student> studentList = StudentDomainTestData.studentList();
        List<StudentDto> studentDtoList = StudentDtoTestData.studentDtoList();
        StudentsDto studentsDto = StudentDtoTestData.studentsDtoList();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterEmail)).thenReturn(headmaster);
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

        String studentsDtoListJson = objectMapper.writeValueAsString(studentsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_ALL_SCHOOL_STUDENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(studentsDtoListJson);
    }


    @Test
    void findAllTeachersApplicationsToCompetition() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();
        String competitionId = ApplicationFormDtoTestData.applicationFormDtoSaved1().competition().competitionId();
        List<ApplicationForm> applicationFormList = ApplicationFormDomainTestData.applicationFormList();
        List<ApplicationFormDto> applicationFormDtoList = ApplicationFormDtoTestData.applicationFormDtoList();
        ApplicationFormsDto applicationFormsDto = ApplicationFormDtoTestData.applicationFormsDtoList();

        //when
        Mockito.when(headmasterService.findHeadmasterByEmail(headmasterEmail)).thenReturn(headmaster);
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

        String applicationFormsDtoListJson = objectMapper.writeValueAsString(applicationFormsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHERS_APPLICATIONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(applicationFormsDtoListJson);
    }


    @Test
    void findCompetitionsCreatedByHeadmaster() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterEmail = headmaster.email();

        List<Competition> competitionList = CompetitionDomainTestData.competitionList();
        List<CompetitionWithLocationDto> competitionDtoList = CompetitionDtoTestData.competitionDtoList();
        CompetitionsDto competitionsDto = CompetitionDtoTestData.competitionsDtoList();

        //when
        Mockito.when(competitionService.findAllCompetitions()).thenReturn(competitionList);
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(0)))
                .thenReturn(competitionDtoList.get(0));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(1)))
                .thenReturn(competitionDtoList.get(1));
        Mockito.when(competitionDtoMapper.mapFromDomainToDto(competitionList.get(2)))
                .thenReturn(competitionDtoList.get(2));

        String competitionsDtoListJson = objectMapper.writeValueAsString(competitionsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + FIND_HEADMASTER_COMPETITIONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("headmasterEmail", headmasterEmail))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(competitionsDtoListJson);
    }

    @Test
    void announceCompetitionResults() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();
        Competition competitionUpdatedAfterResults = competition.withFinished(true);

        List<CompetitionResult> competitionResultListSaved = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoListSaved = CompetitionResultDtoTestData.competitionResultDtoListSaved();

        CompetitionResultListDto competitionResultsDtoToSave = CompetitionResultDtoTestData.competitionResultsDtoToSave();
        CompetitionResultsDto competitionResultsDtoListSaved = CompetitionResultDtoTestData.competitionResultsDtoSaved();

        Student student = StudentDomainTestData.studentSaved1();
        String studentId = student.studentId();

        //when
        Mockito.when(competitionService.findCompetitionById(competitionId)).thenReturn(competition);
        Mockito.when(competitionService.updateCompetitionAfterResults(competition))
                .thenReturn(competitionUpdatedAfterResults);
        Mockito.when(studentService.findStudentById(studentId)).thenReturn(student);
        Mockito.when(competitionResultService.insertAllCompetitionResults(Mockito.anyList()))
                .thenReturn(competitionResultListSaved);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(0)))
                .thenReturn(competitionResultDtoListSaved.get(0));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(1)))
                .thenReturn(competitionResultDtoListSaved.get(1));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(2)))
                .thenReturn(competitionResultDtoListSaved.get(2));

        String resultListDtoToSaveJson = objectMapper.writeValueAsString(competitionResultsDtoToSave);
        String resultListDtoSavedJson = objectMapper.writeValueAsString(competitionResultsDtoListSaved);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_RESULT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("competitionId", competitionId)
                        .content(resultListDtoToSaveJson))
                .andExpect(status().isCreated())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(resultListDtoSavedJson);
    }

    @Test
    void checkAllCompetitionResults() throws Exception {
        //given
        Competition competition = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competition.competitionId();

        List<CompetitionResult> competitionResultListSaved = CompetitionResultDomainTestData.competitionResultListSaved();
        List<CompetitionResultDto> competitionResultDtoListSaved = CompetitionResultDtoTestData.competitionResultDtoListSaved();

        CompetitionResultsDto competitionResultsDtoListSaved = CompetitionResultDtoTestData.competitionResultsDtoSaved();

        //when
        Mockito.when(competitionResultService.findAllCompetitionResults())
                .thenReturn(competitionResultListSaved);
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(0)))
                .thenReturn(competitionResultDtoListSaved.get(0));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(1)))
                .thenReturn(competitionResultDtoListSaved.get(1));
        Mockito.when(competitionResultDtoMapper.mapFromDomainToDto(competitionResultListSaved.get(2)))
                .thenReturn(competitionResultDtoListSaved.get(2));

        String resultListDtoSavedJson = objectMapper.writeValueAsString(competitionResultsDtoListSaved);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                HEADMASTER_REST_MAIN_PAGE + CHECK_ALL_RESULT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("competitionId", competitionId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(resultListDtoSavedJson);
    }
}