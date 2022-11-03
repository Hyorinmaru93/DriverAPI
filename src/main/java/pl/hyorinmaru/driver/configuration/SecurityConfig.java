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

//        User user1 = new User("pendrakar@gmail.com", getBCryptPasswordEncoder().encode("user123"));

        User user1 = User.builder()
                .email("pendrakar@gmail.com")
                .password(getBCryptPasswordEncoder().encode("user123"))
                .build();
        userRepo.save(user1);

        Role role = roleService.readRoleByName("ROLE_USER");
        System.out.println(role);

        user1.setRoles(Set.of(role));

        userService.create(user1);

//        userRepo.save(user1);
//        userService.create(user1);

//        User user2 = new User("jelinski@gmail.com", getBCryptPasswordEncoder().encode("admin123"));
//        userRepo.save(user2);
//        userService.create(user2);

    }

}
