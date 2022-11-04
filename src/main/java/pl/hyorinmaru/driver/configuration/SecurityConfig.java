package pl.hyorinmaru.driver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.hyorinmaru.driver.filter.JwtTokenFilter;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.repository.UserRepo;
import pl.hyorinmaru.driver.service.RoleService;
import pl.hyorinmaru.driver.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepo userRepo;

    private final UserService userService;

    private final JwtTokenFilter jwtTokenFilter;

    private final RoleService roleService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/hej").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with email not found: " + username)
        );
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createUser() {

        Role user = new Role();
        user.setName(Role.Type.ROLE_USER);
        Role admin = new Role();
        admin.setName(Role.Type.ROLE_ADMIN);
        roleService.create(user);
        roleService.create(admin);

        User user1 = User.builder()
                .email("pendrakar@gmail.com")
                .password(getBCryptPasswordEncoder().encode("user123"))
                .build();
        userService.create(user1);

        user1.setRoles(Set.of(roleService.readRoleById(1L)));
        userService.create(user1);

        User user2 = User.builder()
                .email("jelinski93@gmail.com")
                .password(getBCryptPasswordEncoder().encode("admin123"))
                .build();
        userService.create(user2);

        user2.setRoles(Set.of(roleService.readRoleById(2L)));
        userService.create(user2);

    }

}
