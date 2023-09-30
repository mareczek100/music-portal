package mareczek100.musiccontests.integration;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.MusicSchoolsDto;
import mareczek100.musiccontests.api.dto.mapper.HeadmasterDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.TeacherDtoMapper;
import mareczek100.musiccontests.business.HeadmasterService;
import mareczek100.musiccontests.business.StudentService;
import mareczek100.musiccontests.business.TeacherService;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.integration.integration_test_support.MusicContestsUserRestControllerITSupport;
import mareczek100.musiccontests.test_configuration.RestAssuredITConfig;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDtoTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MusicContestsUserIT extends RestAssuredITConfig implements MusicContestsUserRestControllerITSupport {

    private final HeadmasterService headmasterService;
    private final HeadmasterDtoMapper headmasterDtoMapper;
    private final TeacherService teacherService;
    private final TeacherDtoMapper teacherDtoMapper;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;

    @BeforeEach
    void thatThatSetUpWorksCorrectlyWorksCorrectly() {
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterService);
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherService);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(studentService);
        org.junit.jupiter.api.Assertions.assertNotNull(studentDtoMapper);
    }
    @Test
    void thatCreateHeadmasterWorksCorrectly() {
        //given
        HeadmasterDto headmasterDto = HeadmasterDtoTestData.headmasterDtoToSave1();
        String password = "Password";
        List<Headmaster> allHeadmastersBefore = headmasterService.findAllHeadmasters();
        MusicSchoolWithAddressDto schoolDtoToSave = MusicSchoolDtoTestData.musicSchoolDtoToSave1();
        MusicSchoolWithAddressDto musicSchoolWithAddress
                = createMusicSchoolWithAddress(schoolDtoToSave);

        //when
        HeadmasterDto insertedHeadmasterDto
                = createHeadmaster(headmasterDto.withMusicSchool(musicSchoolWithAddress), password);
        Headmaster insertedHeadmaster = headmasterDtoMapper.mapFromDtoToDomain(insertedHeadmasterDto);
        List<Headmaster> allHeadmastersAfter = headmasterService.findAllHeadmasters();

        //then
        Assertions.assertThatCollection(allHeadmastersBefore).doesNotContain(insertedHeadmaster);
        Assertions.assertThatCollection(allHeadmastersAfter).contains(insertedHeadmaster);
        Assertions.assertThat(headmasterDto).usingRecursiveComparison()
                .ignoringFields("headmasterId", "pesel", "musicSchool.musicSchoolId")
                .isEqualTo(insertedHeadmasterDto);
    }

    @Test
    void thatCreateTeacherWorksCorrectly() {
        //given
        TeacherDto teacherDto = TeacherDtoTestData.teacherDtoToSave1();
        String password = "Password";
        List<Teacher> allTeachersBefore = teacherService.findAllTeachers();
        MusicSchoolWithAddressDto musicSchoolWithAddressDto
                = findAllMusicSchools().musicSchoolDtoList().stream().findAny().orElseThrow();

        //when
        TeacherDto insertedTeacherDto
                = createTeacher(teacherDto.withMusicSchool(musicSchoolWithAddressDto), password);
        Teacher insertedTeacher = teacherDtoMapper.mapFromDtoToDomain(insertedTeacherDto);
        List<Teacher> allTeachersAfter = teacherService.findAllTeachers();

        //then
        Assertions.assertThatCollection(allTeachersBefore).doesNotContain(insertedTeacher);
        Assertions.assertThatCollection(allTeachersAfter).contains(insertedTeacher);
    }

    @Test
    void thatCreateStudentWorksCorrectly()
    {
        //given
        StudentDto studentDto = StudentDtoTestData.studentDtoToSave1();
        String password = "Password";
        List<Student> allStudentsBefore = studentService.findAllStudents();
        MusicSchoolWithAddressDto musicSchoolWithAddressDto
                = findAllMusicSchools().musicSchoolDtoList().stream().findAny().orElseThrow();
        TeacherDto teacherDto = TeacherDtoTestData.teacherDtoToSave1();
        TeacherDto insertedTeacherDto
                = createTeacher(teacherDto.withMusicSchool(musicSchoolWithAddressDto), password);

        //when
        StudentDto insertedStudentDto = createStudent(studentDto
                .withMusicSchool(musicSchoolWithAddressDto)
                .withTeacher(insertedTeacherDto), password);
        Student insertedStudent = studentDtoMapper.mapFromDtoToDomain(insertedStudentDto);
        List<Student> allStudentsAfter = studentService.findAllStudents();

        //then
        Assertions.assertThatCollection(allStudentsBefore)
                .doesNotContain(insertedStudent);
        Assertions.assertThatCollection(allStudentsAfter).contains(insertedStudent);
    }

    @Test
    void thatFindMusicSchoolByIdWorksCorrectly()
    {
        //given
        MusicSchoolWithAddressDto musicSchoolDto
                = findAllMusicSchools().musicSchoolDtoList().stream().findAny().orElseThrow();
        String musicSchoolId = musicSchoolDto.musicSchoolId();

        //when

        MusicSchoolWithAddressDto musicSchoolById = findMusicSchoolById(musicSchoolId);

        //then
        Assertions.assertThat(musicSchoolById).isEqualTo(musicSchoolDto);
    }
    @Test
    void thatFindMusicSchoolByPatronWorksCorrectly()
    {
        //given
        MusicSchoolWithAddressDto musicSchoolDto
                = findAllMusicSchools().musicSchoolDtoList().stream().findAny().orElseThrow();
        String musicSchoolPatron = musicSchoolDto.musicSchoolPatron();

        //when

        MusicSchoolWithAddressDto musicSchoolByPatron = findMusicSchoolByPatron(musicSchoolPatron);

        //then
        Assertions.assertThat(musicSchoolByPatron).isEqualTo(musicSchoolDto);
    }
    @Test
    void thatFindMusicSchoolByPatronResponseMessageIfSchoolWithSuchPatronDoesNotExists()
    {
        //given
        String musicSchoolPatron = "No existing patron";
        String problemDetail = "Music school with patron [%s] doesn't exist!".formatted(musicSchoolPatron);

        //when
        Response patronDoesNotExists
                = findMusicSchoolByPatronResponseMessageIfSchoolWithSuchPatronDoesNotExists(musicSchoolPatron);

        //then
        Assertions.assertThat(patronDoesNotExists.asPrettyString()).contains(problemDetail);
        Assertions.assertThat(patronDoesNotExists.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void thatFindAllMusicSchoolsWorksCorrectly() {
        //given, when
        MusicSchoolsDto allMusicSchools = findAllMusicSchools();

        //then
        Assertions.assertThatCollection(allMusicSchools.musicSchoolDtoList()).isNotEmpty();
    }
    @Test
    void thatCreateMusicSchoolWithAddressWorksCorrectly() {
        //given
        MusicSchoolWithAddressDto musicSchoolDtoToSave = MusicSchoolDtoTestData.musicSchoolDtoToSave1();
        List<MusicSchoolWithAddressDto> musicSchoolDtoListBeforeCreate = findAllMusicSchools().musicSchoolDtoList();

        //when
        MusicSchoolWithAddressDto insertedMusicSchoolWithAddress
                = createMusicSchoolWithAddress(musicSchoolDtoToSave);
        List<MusicSchoolWithAddressDto> musicSchoolDtoListAfterCreate = findAllMusicSchools().musicSchoolDtoList();

        //then
        Assertions.assertThatCollection(musicSchoolDtoListBeforeCreate)
                .doesNotContain(insertedMusicSchoolWithAddress);
        Assertions.assertThatCollection(musicSchoolDtoListAfterCreate)
                .contains(insertedMusicSchoolWithAddress);
        Assertions.assertThat(insertedMusicSchoolWithAddress).usingRecursiveComparison()
                .ignoringFields("musicSchoolId").isEqualTo(musicSchoolDtoToSave);
    }

    @Test
    void thatDeleteMusicContestsUserHeadmasterAccountWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        String existingHeadmasterEmail = headmaster.email();

        //when
        Response response = deleteMusicContestsUserAccount(existingHeadmasterEmail);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void thatDeleteMusicContestsUserHeadmasterTeacherAccountWorksCorrectly() {
        //given
        List<String> teacherEmailList = teacherService.findAllTeachers().stream().map(Teacher::email).toList();
        Headmaster headmasterTeacher = headmasterService.findAllHeadmasters().stream()
                .filter(headmaster -> teacherEmailList.contains(headmaster.email()))
                .findAny().orElseThrow();
        String existingHeadmasterTeacherEmail = headmasterTeacher.email();

        //when
        Response response = deleteMusicContestsUserAccount(existingHeadmasterTeacherEmail);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void thatDeleteMusicContestsUserTeacherAccountWorksCorrectly() {
        //given
        Teacher teacher = teacherService.findAllTeachers().stream().findAny().orElseThrow();
        String existingTeacherEmail = teacher.email();

        //when
        Response response = deleteMusicContestsUserAccount(existingTeacherEmail);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void thatDeleteMusicContestsUserStudentAccountWorksCorrectly() {
        //given
        Student student = studentService.findAllStudents().stream().findAny().orElseThrow();
        String existingStudentEmail = student.email();

        //when
        Response response = deleteMusicContestsUserAccount(existingStudentEmail);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void thatDeleteMusicContestsUserAccountThrowsExceptionIfBadFormatEmailProvided() {
        //given
        String badInputEmail = "bad email input";
        String exceptionMessage = "must be a well-formed email address";

        //when
        Response response = deleteMusicContestsUserThrowsExceptionIfBadInput(badInputEmail);

        //then
        Assertions.assertThat(response.asPrettyString()).contains(exceptionMessage);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    void thatDeleteMusicContestsUserAccountThrowsExceptionIfUserDoesNotExist() {
        //given
        String badInputEmail = "noExistingUser@mail.com";
        String exceptionMessage = "Music Contests Portal user [%s] doesn't exist!".formatted(badInputEmail);

        //when
        Response response = deleteMusicContestsUserThrowsExceptionIfBadInput(badInputEmail);

        //then
        Assertions.assertThat(response.asPrettyString()).contains(exceptionMessage);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}