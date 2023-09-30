package mareczek100.musiccontests.infrastructure.security;

import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.infrastructure.configuration.security.MusicContestsUserDetails;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class MusicContestsUserDetailsTest {

    @Mock
    private MusicContestsPortalUserJpaRepository portalUserJpaRepository;
    @Mock
    private RoleJpaRepository roleJpaRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MusicContestsUserDetails musicContestsUserDetails;

    @BeforeEach
    void setUp() {
        Assertions.assertThat(portalUserJpaRepository).isNotNull();
        Assertions.assertThat(roleJpaRepository).isNotNull();
        Assertions.assertThat(musicContestsUserDetails).isNotNull();
        Assertions.assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void loadUserByUsernameWorksCorrectly() {
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

        List<SimpleGrantedAuthority> simpleGrantedAuthorities
                = List.of(new SimpleGrantedAuthority(studentRoleEntity.getRoleName().name()));

        User buildedSecurityUser = new User(
                studentPortalUserEntitySaved.getUserName(),
                studentPortalUserEntitySaved.getPassword(),
                studentPortalUserEntitySaved.getActive(),
                true, true, true,
                simpleGrantedAuthorities
        );

        //when
        Mockito.when(portalUserJpaRepository.findByUserName(studentPortalUserEntitySaved.getUserName()))
                .thenReturn(Optional.of(studentPortalUserEntitySaved));

        UserDetails loadedUserByUsername = musicContestsUserDetails.loadUserByUsername(studentSaved.email());

        //then
        Assertions.assertThat(loadedUserByUsername).isEqualTo(buildedSecurityUser);
    }
    @Test
    void loadUserByUsernameThrowsExceptionIfUserDoesNotExists() {
        //given
        String userDoesNotExistsEmail = "userDoesNotExistsEmail";

        Exception exception = new RuntimeException("User [%s] doesn't exist! Create an account first."
                .formatted(userDoesNotExistsEmail));


        //when
        Mockito.when(portalUserJpaRepository.findByUserName(userDoesNotExistsEmail))
                .thenThrow(exception);
        Executable nonExistingUserException = () ->
                musicContestsUserDetails.loadUserByUsername(userDoesNotExistsEmail);

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, nonExistingUserException, exception.getMessage());
    }
}