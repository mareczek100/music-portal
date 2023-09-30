package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.HeadmasterRepositoryDAO;
import mareczek100.musiccontests.business.dao.TeacherRepositoryDAO;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.infrastructure.security.MusicContestsPortalUserEntity;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.infrastructure.security.SecurityService;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepositoryDAO teacherRepositoryDAO;
    @Mock
    private HeadmasterRepositoryDAO headmasterRepositoryDAO;
    @Mock
    private MusicSchoolService musicSchoolService;
    @Mock
    private SecurityService securityService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(teacherRepositoryDAO);
        Assertions.assertNotNull(headmasterRepositoryDAO);
        Assertions.assertNotNull(musicSchoolService);
        Assertions.assertNotNull(securityService);
        Assertions.assertNotNull(teacherService);
        Assertions.assertNotNull(passwordEncoder);
    }

    @Test
    void insertTeacher() {
        //given
        Teacher teacherToSave = TeacherDomainTestData.teacherToSave1();
        Teacher teacherSaved = TeacherDomainTestData.teacherSaved1();
        String encodedPesel = teacherSaved.pesel();
        List<Teacher> teacherList = TeacherDomainTestData.teacherList();
        List<Teacher> teachers = new ArrayList<>(teacherList);
        teachers.remove(teacherSaved);

        RoleEntity.RoleName teacherRole = RoleEntity.RoleName.TEACHER;
        MusicContestsPortalUserEntity teacherPortalUserEntity
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.fromString("d68f2365-2145-4640-935e-68d3ee603830"))
                .userName(teacherToSave.email())
                .password(teacherToSave.password())
                .build();

        //when
        Mockito.when(teacherRepositoryDAO.findAllTeachers()).thenReturn(teachers);
        Mockito.when(headmasterRepositoryDAO.findHeadmasterByEmail(teacherToSave.email()))
                .thenReturn(Optional.empty());
        Mockito.when(securityService.setRoleWhileCreateNewPortalUser(
                teacherToSave.email(), teacherToSave.password(), teacherRole)).thenReturn(teacherPortalUserEntity);
        Mockito.when(passwordEncoder.encode(teacherToSave.pesel())).thenReturn(encodedPesel);
        Mockito.when(teacherRepositoryDAO.insertTeacher(teacherToSave.withPesel(encodedPesel)))
                .thenReturn(teacherSaved);
        Teacher insertedTeacher = teacherService.insertTeacher(teacherToSave);

        //then
        Assertions.assertEquals(insertedTeacher, teacherSaved);
    }

    @Test
    void findAllTeachers() {
        //given
        Teacher teacherSaved1 = TeacherDomainTestData.teacherSaved1();
        Teacher teacherSaved2 = TeacherDomainTestData.teacherSaved2();
        Teacher teacherSaved3 = TeacherDomainTestData.teacherSaved3();
        List<Teacher> teachersSaved
                = List.of(teacherSaved1, teacherSaved2, teacherSaved3);

        //when
        Mockito.when(teacherRepositoryDAO.findAllTeachers()).thenReturn(teachersSaved);
        List<Teacher> teacherList = teacherService.findAllTeachers();

        //then
        Assertions.assertEquals(teacherList, teachersSaved);
    }

    @Test
    void checkIfTeacherExistByPeselThrowsExceptionIfExists() {
        //given
        String teacherPesel = "12222222222";
        Teacher teacher = TeacherDomainTestData.teacherSaved1();
        String exceptionMessage = "Teacher with pesel [%s] already exist!".formatted(teacherPesel);

        //when
        Mockito.when(teacherRepositoryDAO.findAllTeachers()).thenReturn(List.of(teacher));
        Executable exception = () -> teacherService.checkIfTeacherExistByPesel(teacherPesel);

        //then
        Assertions.assertThrowsExactly(RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findTeacherByEmail() {
        //given
        String teacherEmail = "teacher-email@music-contests.com";
        Teacher teacher = TeacherDomainTestData.teacherSaved1().withEmail(teacherEmail);


        //when
        Mockito.when(teacherRepositoryDAO.findTeacherByEmail(teacherEmail))
                .thenReturn(Optional.of(teacher));
        Teacher teacherByEmail = teacherService.findTeacherByEmail(teacherEmail);

        //then
        Assertions.assertEquals(teacher, teacherByEmail);
    }

    @Test
    void deleteTeacher() {
        //given
        Teacher teacherSaved1 = TeacherDomainTestData.teacherSaved1();
        List<Teacher> teachersSaved = new ArrayList<>(List.of(teacherSaved1));

        //when
        Mockito.when(teacherRepositoryDAO.findAllTeachers()).thenReturn(teachersSaved);
        List<Teacher> teacherListBefore = teacherService.findAllTeachers();
        Assertions.assertEquals(teacherListBefore, teachersSaved);
        teacherService.deleteTeacher(teacherSaved1);
        teachersSaved.remove(teacherSaved1);
        List<Teacher> teacherListAfter = teacherService.findAllTeachers();

        //then
        org.assertj.core.api.Assertions.assertThatCollection(teacherListAfter).isEmpty();
        Mockito.verify(teacherRepositoryDAO).deleteTeacher(teacherSaved1);
    }
}