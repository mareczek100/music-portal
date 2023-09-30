package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.StudentRepositoryDAO;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.infrastructure.security.MusicContestsPortalUserEntity;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.infrastructure.security.SecurityService;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepositoryDAO studentRepositoryDAO;
    @Mock
    private MusicSchoolService musicSchoolService;
    @Mock
    private SecurityService securityService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(studentRepositoryDAO);
        Assertions.assertNotNull(musicSchoolService);
        Assertions.assertNotNull(securityService);
        Assertions.assertNotNull(passwordEncoder);
        Assertions.assertNotNull(studentService);
    }

    @Test
    void insertStudent() {
        //given
        Student studentToSave = StudentDomainTestData.studentToSave1();
        Student studentSaved = StudentDomainTestData.studentSaved1();
        String encodedPesel = studentSaved.pesel();
        List<Student> studentList = Collections.emptyList();

        RoleEntity.RoleName studentRole = RoleEntity.RoleName.STUDENT;
        MusicContestsPortalUserEntity studentPortalUserEntity
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.fromString("0c7e85f7-4fe6-470b-8247-2d35e22809fb"))
                .userName(studentToSave.email())
                .password(studentToSave.password())
                .build();

        //when
        Mockito.when(studentRepositoryDAO.findAllStudents()).thenReturn(studentList);
        Mockito.when(securityService.setRoleWhileCreateNewPortalUser(
                studentToSave.email(), studentToSave.password(), studentRole)).thenReturn(studentPortalUserEntity);
        Mockito.when(passwordEncoder.encode(studentToSave.pesel())).thenReturn(encodedPesel);
        Mockito.when(studentRepositoryDAO.insertStudent(studentToSave.withPesel(encodedPesel)))
                .thenReturn(studentSaved);
        Student insertedStudent = studentService.insertStudent(studentToSave);

        //then
        Assertions.assertEquals(insertedStudent, studentSaved);
    }

    @Test
    void findAllStudents() {
        //given
        Student studentSaved1 = StudentDomainTestData.studentSaved1();
        Student studentSaved2 = StudentDomainTestData.studentSaved2();
        Student studentSaved3 = StudentDomainTestData.studentSaved3();
        List<Student> studentsSaved
                = List.of(studentSaved1, studentSaved2, studentSaved3);

        //when
        Mockito.when(studentRepositoryDAO.findAllStudents()).thenReturn(studentsSaved);
        List<Student> studentList = studentService.findAllStudents();

        //then
        Assertions.assertEquals(studentList, studentsSaved);
    }

    @Test
    void checkIfStudentExistByPeselThrowsExceptionIfExists() {
        //given
        String studentPesel = "13333333333";
        Student student = StudentDomainTestData.studentSaved1();
        String exceptionMessage = "Student with pesel [%s] already exist!".formatted(studentPesel);

        //when
        Mockito.when(studentRepositoryDAO.findAllStudents()).thenReturn(List.of(student));
        Executable exception = () -> studentService.checkIfStudentExistByPesel(studentPesel);

        //then
        Assertions.assertThrowsExactly(RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findStudentByEmail() {
        //given
        String studentEmail = "student-email@music-contests.com";
        Student student = StudentDomainTestData.studentSaved1().withEmail(studentEmail);


        //when
        Mockito.when(studentRepositoryDAO.findStudentByEmail(studentEmail))
                .thenReturn(Optional.of(student));
        Student studentByEmail = studentService.findStudentByEmail(studentEmail);

        //then
        Assertions.assertEquals(student, studentByEmail);
    }

    @Test
    void findStudentById() {
        //given
        Student studentSaved1 = StudentDomainTestData.studentSaved1();
        String studentId = studentSaved1.studentId();

        //when
        Mockito.when(studentRepositoryDAO.findStudentById(studentId))
                .thenReturn(Optional.of(studentSaved1));
        Student studentById = studentService.findStudentById(studentId);

        //then
        Assertions.assertEquals(studentById, studentSaved1);
    }

    @Test
    void deleteStudent() {
        //given
        Student studentSaved1 = StudentDomainTestData.studentSaved1();
        List<Student> studentsSaved = new ArrayList<>(List.of(studentSaved1));

        //when
        Mockito.when(studentRepositoryDAO.findAllStudents()).thenReturn(studentsSaved);
        List<Student> studentListBefore = studentService.findAllStudents();
        Assertions.assertEquals(studentListBefore, studentsSaved);
        studentService.deleteStudent(studentSaved1);
        studentsSaved.remove(studentSaved1);
        List<Student> studentListAfter = studentService.findAllStudents();

        //then
        org.assertj.core.api.Assertions.assertThatCollection(studentListAfter).isEmpty();
        Mockito.verify(studentRepositoryDAO).deleteStudent(studentSaved1);
    }
}