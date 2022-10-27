package pl.hyorinmaru.driver.configuration;

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
import pl.hyorinmaru.driver.model.AppUser;
import pl.hyorinmaru.driver.repository.AppUserRepository;

@Configuration
public class SecurityConfig {

    private AppUserRepository appUserRepository;

    public SecurityConfig(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests((autz) -> autz.antMatchers("/auth/login").permitAll().anyRequest().authenticated()).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //ToRefactor
    @EventListener(ApplicationReadyEvent.class)
    public void saveUser() {
        AppUser appUser = new AppUser("jelinski@gmail.com", getBCryptPasswordEncoder().encode("admin123"));
        appUserRepository.save(appUser);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> appUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
    }
}
