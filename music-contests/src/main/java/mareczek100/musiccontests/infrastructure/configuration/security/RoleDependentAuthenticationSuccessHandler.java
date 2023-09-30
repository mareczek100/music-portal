package mareczek100.musiccontests.infrastructure.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static mareczek100.musiccontests.api.controller.AdminController.ADMIN_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.HeadmasterController.HEADMASTER_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.MainPageController.MUSIC_CONTESTS_AUTHENTICATION;
import static mareczek100.musiccontests.api.controller.MainPageController.MUSIC_CONTESTS_FAILURE;
import static mareczek100.musiccontests.api.controller.StudentController.STUDENT_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.TeacherController.TEACHER_MAIN_PAGE;

@Slf4j
public class RoleDependentAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to: {}", targetUrl);
            throw new RuntimeException("Sorry, cannot redirect to [%s].".formatted(targetUrl));
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrl = new HashMap<>();
        roleTargetUrl.put(RoleEntity.RoleName.ADMIN.name(), ADMIN_MAIN_PAGE);
        roleTargetUrl.put(RoleEntity.RoleName.HEADMASTER.name(), HEADMASTER_MAIN_PAGE);
        roleTargetUrl.put(RoleEntity.RoleName.TEACHER.name(), TEACHER_MAIN_PAGE);
        roleTargetUrl.put(RoleEntity.RoleName.STUDENT.name(), STUDENT_MAIN_PAGE);

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityRoleName = grantedAuthority.getAuthority();
            return roleTargetUrl.getOrDefault(authorityRoleName,
                    MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_FAILURE);
        }
        throw new RuntimeException("Sorry, there is problem with Your authentication.");
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
