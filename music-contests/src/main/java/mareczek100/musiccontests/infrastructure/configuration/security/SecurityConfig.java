package mareczek100.musiccontests.infrastructure.configuration.security;

import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static mareczek100.musiccontests.api.controller.AdminController.ADMIN_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.HeadmasterController.HEADMASTER_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.MainPageController.*;
import static mareczek100.musiccontests.api.controller.StudentController.STUDENT_MAIN_PAGE;
import static mareczek100.musiccontests.api.controller.TeacherController.TEACHER_MAIN_PAGE;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ALL_SUBPAGES = "/**";
    private static final String IMAGES = "/images";
    private static final String REST_API_HOME = "/api";
    private static final String REST_API_CONTRACT = "/swagger-ui";
    private static final String REST_API_DOCS = "/v3/api-docs";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        http.requestCache(cache ->
                        cache.requestCache(requestCache))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(MUSIC_CONTESTS_AUTHENTICATION + ALL_SUBPAGES).permitAll()
                                .requestMatchers(MUSIC_CONTESTS_ERROR).permitAll()
                                .requestMatchers(IMAGES + "/error.jpg").permitAll()
                                .requestMatchers(IMAGES + ALL_SUBPAGES).authenticated()
                                .requestMatchers(ADMIN_MAIN_PAGE + ALL_SUBPAGES)
                                .hasAuthority(RoleEntity.RoleName.ADMIN.name())
                                .requestMatchers(HEADMASTER_MAIN_PAGE + ALL_SUBPAGES)
                                .hasAnyAuthority(RoleEntity.RoleName.HEADMASTER.name(), RoleEntity.RoleName.ADMIN.name())
                                .requestMatchers(TEACHER_MAIN_PAGE + ALL_SUBPAGES)
                                .hasAnyAuthority(RoleEntity.RoleName.TEACHER.name(), RoleEntity.RoleName.ADMIN.name())
                                .requestMatchers(STUDENT_MAIN_PAGE + ALL_SUBPAGES)
                                .hasAnyAuthority(RoleEntity.RoleName.STUDENT.name(), RoleEntity.RoleName.ADMIN.name())
                                .requestMatchers(REST_API_HOME + ALL_SUBPAGES).permitAll()
                                .requestMatchers(REST_API_CONTRACT + ALL_SUBPAGES).permitAll()
                                .requestMatchers(REST_API_DOCS + ALL_SUBPAGES).permitAll())
                .formLogin(formLogin ->
                        formLogin
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGIN)
                                .successHandler(roleDependentAuthenticationSuccessHandler())
                                .failureUrl(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_FAILURE))
                .logout(logout ->
                        logout
                                .logoutUrl(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGOUT)
                                .logoutSuccessUrl(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGOUT_SUCCESS)
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }
    @Bean
    public AuthenticationSuccessHandler roleDependentAuthenticationSuccessHandler() {
        return new RoleDependentAuthenticationSuccessHandler();
    }

}