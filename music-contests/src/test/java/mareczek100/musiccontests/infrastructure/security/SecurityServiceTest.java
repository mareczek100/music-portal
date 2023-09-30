package mareczek100.musiccontests.infrastructure.security;

import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.infrastructure.security.repository.MusicContestsPortalUserJpaRepository;
import mareczek100.musiccontests.infrastructure.security.repository.RoleJpaRepository;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private MusicContestsPortalUserJpaRepository portalUserJpaRepository;
    @Mock
    private RoleJpaRepository roleJpaRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        Assertions.assertThat(portalUserJpaRepository).isNotNull();
        Assertions.assertThat(roleJpaRepository).isNotNull();
        Assertions.assertThat(securityService).isNotNull();
        Assertions.assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void insertRoleWhileCreateNewUserWorksCorrectly() {
        //given
        Student studentToSave = StudentDomainTestData.studentToSave1();
        Student studentSaved = StudentDomainTestData.studentSaved1();
        RoleEntity studentRole = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.STUDENT)
                .build();

        MusicContestsPortalUserEntity studentPortalUserEntityToSave
                = MusicContestsPortalUserEntity.builder()
                .userName(studentToSave.email())
                .password(studentToSave.pesel())
                .active(true)
                .role(studentRole)
                .build();

        MusicContestsPortalUserEntity studentPortalUserEntitySaved
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.randomUUID())
                .userName(studentToSave.email())
                .password(studentSaved.pesel())
                .active(true)
                .role(studentRole)
                .build();

        //when
        Mockito.when(roleJpaRepository.findRoleByRoleName(studentRole.getRoleName()))
                .thenReturn(Optional.of(studentRole));
        Mockito.when(portalUserJpaRepository.findAll()).thenReturn(
                List.of(studentPortalUserEntitySaved
                        .withUserName("other user name")
                        .withPassword("other user password")));
        Mockito.when(passwordEncoder.encode(studentToSave.pesel()))
                .thenReturn(studentSaved.pesel());
        Mockito.when(portalUserJpaRepository.saveAndFlush(studentPortalUserEntityToSave))
                .thenReturn(studentPortalUserEntitySaved);
        MusicContestsPortalUserEntity insertedMusicContestsPortalUser = securityService.setRoleWhileCreateNewPortalUser(
                studentToSave.email(), studentToSave.pesel(), RoleEntity.RoleName.STUDENT
        );

        //then
        Assertions.assertThat(insertedMusicContestsPortalUser).isEqualTo(studentPortalUserEntitySaved);
        Assertions.assertThat(insertedMusicContestsPortalUser.userName)
                .isEqualTo(studentToSave.email());
        Assertions.assertThat(insertedMusicContestsPortalUser.role)
                .isEqualTo(studentRole);
    }
    @Test
    void insertRoleWhileCreateNewUserThrowsExceptionIfRoleDoesNotExists() {
        //given
        String forExampleDeletedNonExistingRole = RoleEntity.RoleName.TEACHER.name();
        Student studentToSave = StudentDomainTestData.studentToSave1();
        Exception exception = new RuntimeException("Role [%s] doesn't exist!"
                .formatted(forExampleDeletedNonExistingRole));

        //when
        Mockito.when(roleJpaRepository.findRoleByRoleName(
                RoleEntity.RoleName.valueOf(forExampleDeletedNonExistingRole))).thenThrow(exception);
        Executable nonExistingRoleException = () -> securityService.setRoleWhileCreateNewPortalUser(
                studentToSave.email(), studentToSave.pesel(),
                RoleEntity.RoleName.valueOf(forExampleDeletedNonExistingRole)
        );

        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class, nonExistingRoleException, exception.getMessage());
    }
    @Test
    void insertRoleWhileCreateNewUserThrowsExceptionIfUserAlreadyExists() {
        //given
        Student studentToSave = StudentDomainTestData.studentToSave1();
        Student studentSaved = StudentDomainTestData.studentSaved1();
        RoleEntity studentRole = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.STUDENT)
                .build();

        MusicContestsPortalUserEntity studentPortalUserEntitySaved
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.randomUUID())
                .userName(studentToSave.email())
                .password(studentSaved.pesel())
                .active(true)
                .role(studentRole)
                .build();

        Exception exception = new RuntimeException("Account for user [%s]: already exist!"
                .formatted(studentToSave.email()));

        //when
        Mockito.when(roleJpaRepository.findRoleByRoleName(studentRole.getRoleName()))
                .thenReturn(Optional.of(studentRole));
        Mockito.when(portalUserJpaRepository.findAll())
                .thenReturn(List.of(studentPortalUserEntitySaved));
        Executable existingUserException = () -> securityService.setRoleWhileCreateNewPortalUser(
                studentToSave.email(), studentToSave.pesel(), RoleEntity.RoleName.STUDENT
        );

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, existingUserException, exception.getMessage());
    }

    @Test
    void changeRoleToExistingUserWorksCorrectly() {
        //given
        Student studentSaved = StudentDomainTestData.studentSaved1();
        RoleEntity studentRoleEntity = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.STUDENT)
                .build();

        RoleEntity teacherRoleEntity = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.TEACHER)
                .build();

        RoleEntity.RoleName teacherRoleName = RoleEntity.RoleName.TEACHER;

        MusicContestsPortalUserEntity studentPortalUserEntitySaved
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.randomUUID())
                .userName(studentSaved.email())
                .password(studentSaved.pesel())
                .active(true)
                .role(studentRoleEntity)
                .build();

        MusicContestsPortalUserEntity studentNewTeacherRolePortalUserEntityToUpdate
                = studentPortalUserEntitySaved.withRole(teacherRoleEntity);


        //when
        Mockito.when(roleJpaRepository.findRoleByRoleName(teacherRoleName)).thenReturn(
                Optional.of(teacherRoleEntity));
        Mockito.when(portalUserJpaRepository.saveAndFlush(
                studentPortalUserEntitySaved.withRole(teacherRoleEntity)))
                .thenReturn(studentNewTeacherRolePortalUserEntityToUpdate);

        MusicContestsPortalUserEntity updatedRoleMusicContestsPortalUser = securityService.setRoleWhileCreateNewPortalUser(
                studentSaved.email(), studentSaved.pesel(), RoleEntity.RoleName.TEACHER
        );

        //then
        Assertions.assertThat(updatedRoleMusicContestsPortalUser)
                .isEqualTo(studentNewTeacherRolePortalUserEntityToUpdate);
        Assertions.assertThat(updatedRoleMusicContestsPortalUser.userName)
                .isEqualTo(studentSaved.email());
        Assertions.assertThat(updatedRoleMusicContestsPortalUser.role.getRoleName())
                .isEqualTo(teacherRoleName);
    }
    @Test
    void changeRoleToExistingUserThrowsExceptionIfRoleDoesNotExists() {
        //given
        Student studentSaved = StudentDomainTestData.studentSaved1();
        String forExampleDeletedNonExistingRole = RoleEntity.RoleName.STUDENT.name();
        Exception exception = new RuntimeException("Role [%s] doesn't exist!"
                .formatted(forExampleDeletedNonExistingRole));


        //when
        Mockito.when(roleJpaRepository.findRoleByRoleName(
                RoleEntity.RoleName.valueOf(forExampleDeletedNonExistingRole)))
                .thenThrow(exception);
        Executable nonExistingRoleException = () -> securityService.changeRoleToExistingUser(
                studentSaved.email(),RoleEntity.RoleName.valueOf(forExampleDeletedNonExistingRole)
        );

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, nonExistingRoleException, exception.getMessage());
    }

    @Test
    void findByUserName() {
        //given
        Student studentSaved = StudentDomainTestData.studentSaved1();
        RoleEntity studentRoleEntity = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.STUDENT)
                .build();

        MusicContestsPortalUserEntity studentPortalUserEntitySaved
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.randomUUID())
                .userName(studentSaved.email())
                .password(studentSaved.pesel())
                .active(true)
                .role(studentRoleEntity)
                .build();

        //when
        Mockito.when(portalUserJpaRepository.findByUserName(studentSaved.email()))
                .thenReturn(Optional.of(studentPortalUserEntitySaved));
        MusicContestsPortalUserEntity existingPortalUser = securityService.findByUserName(studentSaved.email());

        //then
        Assertions.assertThat(existingPortalUser).isEqualTo(studentPortalUserEntitySaved);
    }
    @Test
    void findByUserNameReturnsNullIfCannotFind() {
        //given
        String nonExistingStudentEmail = "nonExistingStudentEmail";

        //when
        Mockito.when(portalUserJpaRepository.findByUserName(nonExistingStudentEmail))
                .thenReturn(Optional.empty());
        MusicContestsPortalUserEntity nonExistingPortalUser
                = securityService.findByUserName(nonExistingStudentEmail);

        //then
        Assertions.assertThat(nonExistingPortalUser).isNull();
    }

    @Test
    void deleteUserByUserName() {
        //given
        Student studentSaved = StudentDomainTestData.studentSaved1();
        RoleEntity studentRoleEntity = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(RoleEntity.RoleName.STUDENT)
                .build();

        MusicContestsPortalUserEntity studentPortalUserEntitySaved
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.randomUUID())
                .userName(studentSaved.email())
                .password(studentSaved.pesel())
                .active(true)
                .role(studentRoleEntity)
                .build();

        //when
        Mockito.when(portalUserJpaRepository.findByUserName(studentSaved.email()))
                .thenReturn(Optional.of(studentPortalUserEntitySaved));
        securityService.deleteUserByUserName(studentSaved.email());

        //then
        Mockito.verify(portalUserJpaRepository).delete(studentPortalUserEntitySaved);
    }
}