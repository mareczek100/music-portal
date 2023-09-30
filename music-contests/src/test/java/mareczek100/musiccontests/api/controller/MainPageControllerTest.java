package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.MusicContestsPortalUserDto;
import mareczek100.musiccontests.api.dto.mapper.*;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDtoTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDtoTestData;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static mareczek100.musiccontests.api.controller.MainPageController.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MainPageController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MainPageControllerTest {


    @MockBean
    private final InstrumentApiService instrumentApiService;
    @MockBean
    private final InstrumentDtoMapper instrumentDtoMapper;
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

    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
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
        Assertions.assertNotNull(applicationFormService);
        Assertions.assertNotNull(competitionResultService);
        Assertions.assertNotNull(competitionService);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void loginHomePage() throws Exception {
        //given
        String welcomeMessage = "Witaj na Portalu Konkursów Muzycznych!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGIN)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(welcomeMessage);
    }

    @Test
    void loginCreateAccountHomePage() throws Exception {
        //given
        List<String> roleList = Arrays.stream(RoleEntity.RoleName.values())
                .filter(roleName -> !roleName.equals(RoleEntity.RoleName.ADMIN))
                .map(Enum::name).toList();

        List<MusicSchoolWithAddressDto> musicSchoolDtoList = MusicSchoolDtoTestData.musicSchoolDtoList();
        List<MusicSchool> musicSchoolList = MusicSchoolDomainTestData.musicSchoolList();

        //when
        Mockito.when(musicSchoolService.findAllMusicSchools()).thenReturn(musicSchoolList);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(0)))
                .thenReturn(musicSchoolDtoList.get(0));
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(1)))
                .thenReturn(musicSchoolDtoList.get(1));
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolList.get(2)))
                .thenReturn(musicSchoolDtoList.get(2));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_create_account"))
                .andExpect(model().attribute("musicSchoolDTOs", musicSchoolDtoList))
                .andExpect(model().attribute("roleList", roleList))
                .andReturn();
    }

    @Test
    void loginCreateHeadmasterWithExistingSchoolAccountProcess() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        Headmaster headmasterSaved = HeadmasterDomainTestData.headmasterSaved1();
        HeadmasterDto headmasterDtoToSave = HeadmasterDtoTestData.headmasterDtoToSave1();
        HeadmasterDto headmasterDtoSaved = HeadmasterDtoTestData.headmasterDtoSaved1();
        MusicSchool musicSchoolExisted = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchoolExisted.musicSchoolId();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", headmasterDtoToSave.name());
        multiValueMap.add("surname", headmasterDtoToSave.surname());
        multiValueMap.add("email", headmasterDtoToSave.email());
        multiValueMap.add("pesel", headmasterDtoToSave.pesel());
        multiValueMap.add("password", "ValidPassword");
        multiValueMap.add("role", RoleEntity.RoleName.HEADMASTER.name());

        //when
        Mockito.when(instrumentApiService.findAllInstruments()).thenReturn(instrumentList);
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(0)))
                .thenReturn(instrumentDtoList.get(0));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(1)))
                .thenReturn(instrumentDtoList.get(1));
        Mockito.when(instrumentDtoMapper.mapFromDomainToDto(instrumentList.get(2)))
                .thenReturn(instrumentDtoList.get(2));
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId)).thenReturn(musicSchoolExisted);
        Mockito.when(headmasterService.insertHeadmaster(Mockito.any(Headmaster.class)))
                .thenReturn(headmasterSaved);
        Mockito.when(headmasterDtoMapper.mapFromDomainToDto(headmasterSaved)).thenReturn(headmasterDtoSaved);

        //then
        MvcResult mvcResult = mockMvc.perform(post(
                        MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
                        .params(multiValueMap)
                        .flashAttr("musicSchoolDto", musicSchoolDto)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", headmasterDtoSaved))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(
                Objects.requireNonNull(mvcResult.getModelAndView()).getStatus())
                .isEqualTo(HttpStatus.CREATED);
    }


    @Test
    void loginCreateTeacherAccountProcessPrepare() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        MusicContestsPortalUserDto portalUserDto = MusicContestsPortalUserDto.builder()
                .name("name")
                .surname("surname")
                .email("teacher.email@email.com")
                .pesel("01234567890")
                .password("ValidPassword")
                .role(RoleEntity.RoleName.TEACHER)
                .build();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", portalUserDto.getName());
        multiValueMap.add("surname", portalUserDto.getSurname());
        multiValueMap.add("email", portalUserDto.getEmail());
        multiValueMap.add("pesel", portalUserDto.getPesel());
        multiValueMap.add("password", portalUserDto.getPassword());
        multiValueMap.add("role", portalUserDto.getRole().name());

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
        mockMvc.perform(post(
                        MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
                        .params(multiValueMap)
                        .flashAttr("musicSchoolDto", musicSchoolDto)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_create_teacher"))
                .andExpect(model().attribute("portalUser", portalUserDto))
                .andExpect(model().attribute("teacherDto", TeacherDto.builder().build()))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("musicSchoolDto", musicSchoolDto))
                .andReturn();
    }
    @Test
    void loginCreateTeacherWithExistingSchoolAccountProcess() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        Teacher teacherSaved = TeacherDomainTestData.teacherSaved1();
        Teacher teacherToSave = TeacherDomainTestData.teacherToSave1();
        TeacherDto teacherDtoToSave = TeacherDtoTestData.teacherDtoToSave1();
        TeacherDto teacherDtoSaved = TeacherDtoTestData.teacherDtoSaved1();
        MusicSchool musicSchoolExisted = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchoolExisted.musicSchoolId();
        String teacherPassword = "TeacherPassword";

        //when
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId)).thenReturn(musicSchoolExisted);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolExisted)).thenReturn(musicSchoolDto);
        Mockito.when(teacherDtoMapper.mapFromDtoToDomain(teacherDtoToSave)).thenReturn(teacherToSave);
        Mockito.when(teacherService.insertTeacher(Mockito.any(Teacher.class)))
                .thenReturn(teacherSaved);
        Mockito.when(teacherDtoMapper.mapFromDomainToDto(teacherSaved)).thenReturn(teacherDtoSaved);

        //then
        mockMvc.perform(post(
                        MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_ACCOUNT_TEACHER)
                        .param("teacherPassword", teacherPassword)
                        .flashAttr("musicSchoolDto", musicSchoolDto)
                        .flashAttr("teacherDto", teacherDtoToSave)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", teacherDtoSaved))
                .andReturn();
    }

    @Test
    void loginCreateStudentAccountProcessPrepare() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();
        List<InstrumentDto> instrumentDtoList = InstrumentDtoTestData.instrumentDtoList();
        List<ClassLevel> classLevels = Arrays.stream(ClassLevel.values()).toList();
        List<EducationProgram> educationPrograms = Arrays.stream(EducationProgram.values()).toList();
        List<Degree> degrees = Arrays.stream(Degree.values()).toList();
        List<Teacher> teacherList = TeacherDomainTestData.teacherList();
        List<TeacherDto> teacherDtoList = TeacherDtoTestData.teacherDtoList();
        MusicContestsPortalUserDto portalUserDto = MusicContestsPortalUserDto.builder()
                .name("name")
                .surname("surname")
                .email("teacher.email@email.com")
                .pesel("01234567890")
                .password("ValidPassword")
                .role(RoleEntity.RoleName.STUDENT)
                .build();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("name", portalUserDto.getName());
        multiValueMap.add("surname", portalUserDto.getSurname());
        multiValueMap.add("email", portalUserDto.getEmail());
        multiValueMap.add("pesel", portalUserDto.getPesel());
        multiValueMap.add("password", portalUserDto.getPassword());
        multiValueMap.add("role", portalUserDto.getRole().name());

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

        //then
        mockMvc.perform(post(
                        MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
                        .params(multiValueMap)
                        .flashAttr("musicSchoolDto", musicSchoolDto)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_create_student"))
                .andExpect(model().attribute("portalUser", portalUserDto))
                .andExpect(model().attribute("studentDto", StudentDto.builder().build()))
                .andExpect(model().attribute("instrumentDTOs", instrumentDtoList))
                .andExpect(model().attribute("musicSchoolDto", musicSchoolDto))
                .andExpect(model().attribute("classLevels", classLevels))
                .andExpect(model().attribute("educationPrograms", educationPrograms))
                .andExpect(model().attribute("degrees", degrees))
                .andExpect(model().attribute("teacherDTOs", teacherDtoList))
                .andReturn();
    }
    @Test
    void loginCreateStudentWithExistingSchoolAccountProcess() throws Exception {
        //given
        MusicSchoolWithAddressDto musicSchoolDto = MusicSchoolDtoTestData.musicSchoolDtoSaved1();
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String teacherEmail = teacher.email();
        Student studentSaved = StudentDomainTestData.studentSaved1();
        Student studentToSave = StudentDomainTestData.studentToSave1();
        StudentDto studentDtoToSave = StudentDtoTestData.studentDtoToSave1();
        StudentDto studentDtoSaved = StudentDtoTestData.studentDtoSaved1();
        MusicSchool musicSchoolExisted = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchoolExisted.musicSchoolId();
        String studentPassword = "StudentPassword";

        //when
        Mockito.when(teacherService.findTeacherByEmail(teacherEmail)).thenReturn(teacher);
        Mockito.when(musicSchoolService.findMusicSchoolById(musicSchoolId)).thenReturn(musicSchoolExisted);
        Mockito.when(musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolExisted)).thenReturn(musicSchoolDto);
        Mockito.when(studentDtoMapper.mapFromDtoToDomain(studentDtoToSave)).thenReturn(studentToSave);
        Mockito.when(studentService.insertStudent(Mockito.any(Student.class)))
                .thenReturn(studentSaved);
        Mockito.when(studentDtoMapper.mapFromDomainToDto(studentSaved)).thenReturn(studentDtoSaved);

        //then
        mockMvc.perform(post(
                        MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_ACCOUNT_STUDENT)
                        .param("teacherEmail", teacherEmail)
                        .param("studentPassword", studentPassword)
                        .flashAttr("musicSchoolDto", musicSchoolDto)
                        .flashAttr("studentDto", studentDtoToSave)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", studentDtoSaved))
                .andReturn();
    }

    @Test
    void deleteMusicContestsHeadmasterUserAccount() throws Exception {
        //given
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String userEmail = headmaster.email();

        //when
        Mockito.when(headmasterService.findAllHeadmasters()).thenReturn(List.of(headmaster));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_DELETE_ACCOUNT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("userEmail", userEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", headmaster))
                .andReturn();
    }
    @Test
    void deleteMusicContestsTeacherUserAccount() throws Exception {
        //given
        List<Teacher> teacherList = TeacherDomainTestData.teacherList();
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String userEmail = teacher.email();

        //when
        Mockito.when(teacherService.findAllTeachers()).thenReturn(teacherList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_DELETE_ACCOUNT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("userEmail", userEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", teacher))
                .andReturn();
    }
    @Test
    void deleteMusicContestsStudentUserAccount() throws Exception {
        //given
        List<Student> studentList = StudentDomainTestData.studentList();
        Student student = StudentDomainTestData.studentSaved1();
        String userEmail = student.email();

        //when
        Mockito.when(studentService.findAllStudents()).thenReturn(studentList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_DELETE_ACCOUNT)
                        .contentType(MediaType.TEXT_HTML)
                        .param("userEmail", userEmail))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_main_page"))
                .andExpect(model().attribute("portalUser", student))
                .andReturn();
    }

    @Test
    void loginFailureHomePage() throws Exception {
        //given
        String failureMessage = "Błędna nazwa użytkownika lub hasło";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_FAILURE)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_failure"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(failureMessage);
    }

    @Test
    void errorHomePage() throws Exception {
        //given
        String errorTittle = "Error site!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(MUSIC_CONTESTS_ERROR)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(errorTittle);
    }

    @Test
    void loginLogoutHomePage() throws Exception {
        //given
        String logoutMessage = "Poprawnie wylogowano!";

        //when, then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGOUT_SUCCESS)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("login/login_logout"))
                .andReturn();

        org.assertj.core.api.Assertions.assertThat(mvcResult.getResponse().getContentAsString())
                .contains(logoutMessage);
    }
}