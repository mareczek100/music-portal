package mareczek100.musiccontests.api.controller.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.MusicSchoolsDto;
import mareczek100.musiccontests.api.dto.mapper.HeadmasterDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.MusicSchoolDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.TeacherDtoMapper;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDtoTestData;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static mareczek100.musiccontests.api.controller.rest_controller.MusicContestsUserRestController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MusicContestsUserRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MusicContestsUserRestControllerTest {


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
    @MockBean
    private final ApplicationFormService applicationFormService;
    @MockBean
    private final CompetitionResultService competitionResultService;
    @MockBean
    private final CompetitionService competitionService;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(teacherService);
        Assertions.assertNotNull(teacherDtoMapper);
        Assertions.assertNotNull(headmasterService);
        Assertions.assertNotNull(headmasterDtoMapper);
        Assertions.assertNotNull(musicSchoolService);
        Assertions.assertNotNull(musicSchoolDtoMapper);
        Assertions.assertNotNull(studentService);
        Assertions.assertNotNull(studentDtoMapper);
        Assertions.assertNotNull(applicationFormService);
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void createHeadmaster() throws Exception {
        //given
        Headmaster headmasterToSave1 = HeadmasterDomainTestData.headmasterToSave1();
        Headmaster headmasterSaved1 = HeadmasterDomainTestData.headmasterSaved1();
        HeadmasterDto headmasterDtoToSave1 = HeadmasterDtoTestData.headmasterDtoToSave1();
        HeadmasterDto headmasterDtoSaved1 = HeadmasterDtoTestData.headmasterDtoSaved1();

        //when
        Mockito.when(headmasterDtoMapper.mapFromDtoToDomain(Mockito.any(HeadmasterDto.class)))
                .thenReturn(headmasterToSave1);
        Mockito.when(headmasterService.insertHeadmaster(headmasterToSave1))
                .thenReturn(headmasterSaved1);
        Mockito.when(headmasterDtoMapper.mapFromDomainToDto(headmasterSaved1))
                .thenReturn(headmasterDtoSaved1);

        String headmasterToSaveDtoJson = objectMapper.writeValueAsString(headmasterDtoToSave1);
        String headmasterSavedDtoJson = objectMapper.writeValueAsString(headmasterDtoSaved1);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_HEADMASTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("password", headmasterToSave1.password())
                        .content(headmasterToSaveDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name",
                        Matchers.is(headmasterDtoSaved1.name())))
                .andExpect(jsonPath("$.email",
                        Matchers.is(headmasterDtoSaved1.email())))
                .andExpect(jsonPath("$.pesel",
                        Matchers.is(headmasterDtoSaved1.pesel())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), headmasterSavedDtoJson);
    }

    @Test
    void createTeacher() throws Exception {
        //given
        Teacher teacherToSave1 = TeacherDomainTestData.teacherToSave1();
        Teacher teacherSaved1 = TeacherDomainTestData.teacherSaved1();
        TeacherDto teacherDtoToSave1 = TeacherDtoTestData.teacherDtoToSave1();
        TeacherDto teacherDtoSaved1 = TeacherDtoTestData.teacherDtoSaved1();

        //when
        Mockito.when(teacherDtoMapper.mapFromDtoToDomain(Mockito.any(TeacherDto.class)))
                .thenReturn(teacherToSave1);
        Mockito.when(teacherService.insertTeacher(teacherToSave1))
                .thenReturn(teacherSaved1);
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherSaved1))
                .thenReturn(teacherDtoSaved1);

        String teacherToSaveDtoJson = objectMapper.writeValueAsString(teacherDtoToSave1);
        String teacherSavedDtoJson = objectMapper.writeValueAsString(teacherDtoSaved1);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_TEACHER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("password", teacherToSave1.password())
                        .content(teacherToSaveDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name",
                        Matchers.is(teacherDtoSaved1.name())))
                .andExpect(jsonPath("$.email",
                        Matchers.is(teacherDtoSaved1.email())))
                .andExpect(jsonPath("$.pesel",
                        Matchers.is(teacherDtoSaved1.pesel())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), teacherSavedDtoJson);
    }

    @Test
    void createStudent() throws Exception {
        //given
        Student studentToSave1 = StudentDomainTestData.studentToSave1();
        Student studentSaved1 = StudentDomainTestData.studentSaved1();
        StudentDto studentDtoToSave1 = StudentDtoTestData.studentDtoToSave1();
        StudentDto studentDtoSaved1 = StudentDtoTestData.studentDtoSaved1();

        //when
        Mockito.when(studentDtoMapper.mapFromDtoToDomain(Mockito.any(StudentDto.class)))
                .thenReturn(studentToSave1);
        Mockito.when(studentService.insertStudent(studentToSave1))
                .thenReturn(studentSaved1);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentSaved1))
                .thenReturn(studentDtoSaved1);

        String studentToSaveDtoJson = objectMapper.writeValueAsString(studentDtoToSave1);
        String studentSavedDtoJson = objectMapper.writeValueAsString(studentDtoSaved1);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_STUDENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("password", studentToSave1.password())
                        .content(studentToSaveDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name",
                        Matchers.is(studentDtoSaved1.name())))
                .andExpect(jsonPath("$.email",
                        Matchers.is(studentDtoSaved1.email())))
                .andExpect(jsonPath("$.pesel",
                        Matchers.is(studentDtoSaved1.pesel())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), studentSavedDtoJson);
    }

    @Test
    void findMusicSchoolById() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        MusicSchool musicSchool = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchoolDto.musicSchoolId();

        //when
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId))
                .thenReturn(musicSchool);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchool))
                .thenReturn(musicSchoolDto);

        String musicSchoolDtoJson = objectMapper.writeValueAsString(musicSchoolDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("musicSchoolId", musicSchoolId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.musicSchoolId",
                        Matchers.is(musicSchoolDto.musicSchoolId())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(musicSchoolDtoJson);
    }

    @Test
    void findMusicSchoolByPatron() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        MusicSchool musicSchool = MusicSchoolDomainTestData.musicSchoolSaved1();
        Optional<MusicSchool> optionalMusicSchool = Optional.ofNullable(musicSchool);
        String musicSchoolPatron = musicSchoolDto.musicSchoolPatron();

        //when
        Mockito.when(musicSchoolService.findMusicSchoolByPatron(musicSchoolPatron))
                .thenReturn(optionalMusicSchool);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(optionalMusicSchool.get()))
                .thenReturn(musicSchoolDto);

        String musicSchoolDtoJson = objectMapper.writeValueAsString(musicSchoolDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_PATRON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patron", musicSchoolPatron))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.musicSchoolPatron",
                        Matchers.is(musicSchoolDto.musicSchoolPatron())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(musicSchoolDtoJson);
    }
    @Test
    void findMusicSchoolByPatronAndResponseProblemDetailIfNoExists() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        String musicSchoolPatron = musicSchoolDto.musicSchoolPatron();
        String problemMessage = "Music school with patron [%s] doesn't exist!".formatted(musicSchoolPatron);
        Optional<MusicSchool> optionalMusicSchool = Optional.empty();

        //when
        Mockito.when(musicSchoolService.findMusicSchoolByPatron(musicSchoolPatron))
                .thenReturn(optionalMusicSchool);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_PATRON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patron", musicSchoolPatron))
                .andExpect(status().isNotFound())
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(problemMessage);
    }

    @Test
    void findAllMusicSchools() throws Exception {
        //given
        List<MusicSchool> musicSchoolList = MusicSchoolDomainTestData.musicSchoolList();
        List<MusicSchoolWithAddressDto> musicSchoolDtoList = MusicSchoolDtoTestData.musicSchoolDtoList();
        MusicSchoolsDto musicSchoolsDto = MusicSchoolDtoTestData.musicSchoolsDto();

        //when
        Mockito.when(musicSchoolService.findAllMusicSchools()).thenReturn(musicSchoolList);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(0)))
                .thenReturn(musicSchoolDtoList.get(0));
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(1)))
                .thenReturn(musicSchoolDtoList.get(1));
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(2)))
                .thenReturn(musicSchoolDtoList.get(2));

        String musicSchoolsDtoJson = objectMapper.writeValueAsString(musicSchoolsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_ALL_MUSIC_SCHOOLS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(musicSchoolsDtoJson);
    }

    @Test
    void createMusicSchoolWithAddress() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDtoToSave1 = MusicSchoolDtoTestData.musicSchoolDtoToSave1();
        MusicSchoolWithAddressDto musicSchoolDtoSaved1 = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        MusicSchool musicSchoolToSave1 = MusicSchoolDomainTestData.musicSchoolToSave1();
        MusicSchool musicSchoolSaved1 = MusicSchoolDomainTestData.musicSchoolSaved1();

        //when
        Mockito.when(musicSchoolDtoMapper.mapFromDtoToDomain(Mockito.any(MusicSchoolWithAddressDto.class)))
                .thenReturn(musicSchoolToSave1);
        Mockito.when(musicSchoolService.insertMusicSchool(musicSchoolToSave1))
                .thenReturn(musicSchoolSaved1);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolSaved1))
                .thenReturn(musicSchoolDtoSaved1);

        String musicSchoolDtoJsonToSave = objectMapper.writeValueAsString(musicSchoolDtoToSave1);
        String musicSchoolDtoJsonSaved = objectMapper.writeValueAsString(musicSchoolDtoSaved1);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_MUSIC_SCHOOL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(musicSchoolDtoJsonToSave))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(musicSchoolDtoJsonSaved);
    }

    @Test
    void deleteMusicContestsUserHeadmasterAccount() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String headmasterUserEmail = headmaster.email();

        //when
        Mockito.when(headmasterService.findAllHeadmasters()).thenReturn(List.of(headmaster));

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userEmail", headmasterUserEmail))
                .andExpect(status().isNoContent())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Mockito.when(headmasterService.findAllHeadmasters()).thenReturn(Collections.emptyList());

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
        org.assertj.core.api.Assertions.assertThatCollection(headmasterService.findAllHeadmasters())
                .isEqualTo(Collections.emptyList());
    }
    @Test
    void deleteMusicContestsUserTeacherAccount() throws Exception {
        //given
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        List<Teacher> teacherList = TeacherDomainTestData.teacherList();
        String teacherUserEmail = teacher.email();

        //when
        Mockito.when(teacherService.findAllTeachers()).thenReturn(teacherList);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userEmail", teacherUserEmail))
                .andExpect(status().isNoContent())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Mockito.when(teacherService.findAllTeachers()).thenReturn(Collections.emptyList());

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
        org.assertj.core.api.Assertions.assertThatCollection(teacherService.findAllTeachers())
                .isEqualTo(Collections.emptyList());
    }
    @Test
    void deleteMusicContestsUserStudentAccount() throws Exception {
        //given
        Student student = StudentDomainTestData.studentSaved1();
        List<Student> studentList = StudentDomainTestData.studentList();
        String studentUserEmail = student.email();

        //when
        Mockito.when(studentService.findAllStudents()).thenReturn(studentList);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                MUSIC_CONTESTS_USER_REST_MAIN_PAGE + MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userEmail", studentUserEmail))
                .andExpect(status().isNoContent())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        Mockito.when(studentService.findAllStudents()).thenReturn(Collections.emptyList());

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEmpty();
        org.assertj.core.api.Assertions.assertThatCollection(studentService.findAllStudents())
                .isEqualTo(Collections.emptyList());
    }
}