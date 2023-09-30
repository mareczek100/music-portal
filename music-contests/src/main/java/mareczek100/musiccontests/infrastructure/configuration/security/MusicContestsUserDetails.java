package mareczek100.musiccontests.infrastructure.configuration.security;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.infrastructure.security.MusicContestsPortalUserEntity;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.infrastructure.security.repository.MusicContestsPortalUserJpaRepository;
import mareczek100.musiccontests.infrastructure.security.repository.RoleJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MusicContestsUserDetails implements UserDetailsService {

    private final MusicContestsPortalUserJpaRepository userRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MusicContestsPortalUserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(
                () -> new RuntimeException("User [%s] doesn't exist! Create an account first."
                        .formatted(userName))
        );
        List<GrantedAuthority> authorities = getUserAuthority(userEntity.getRole());
        return buildUserForAuthentication(userEntity, authorities);
    }

    @PostConstruct
    public void init() {
        RoleEntity.RoleName admin = RoleEntity.RoleName.ADMIN;
        RoleEntity roleAdmin = roleJpaRepository.findRoleByRoleName(admin)
                .orElseThrow(() -> new RuntimeException("Role [%s] doesn't exist!"
                        .formatted(admin)));

        MusicContestsPortalUserEntity adminUserEntity = MusicContestsPortalUserEntity.builder()
                .userName("admin@music-contests.pl")
                .password(passwordEncoder.encode("00000000000"))
                .active(true)
                .role(roleAdmin)
                .build();
        if (userRepository.findByUserName(adminUserEntity.getUserName()).isPresent()){
            return;
        }
        userRepository.saveAndFlush(adminUserEntity);
    }

    private List<GrantedAuthority> getUserAuthority(RoleEntity userRole) {
        return List.of(new SimpleGrantedAuthority(userRole.getRoleName().name()));
    }

    private UserDetails buildUserForAuthentication(MusicContestsPortalUserEntity userEntity, List<GrantedAuthority> authorities) {
        return new User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getActive(),
                true, true, true,
                authorities
        );
    }
}