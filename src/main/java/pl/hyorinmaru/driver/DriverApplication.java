package pl.hyorinmaru.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.service.UserService;
import pl.hyorinmaru.driver.service.implementation.RoleServiceImplementation;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class DriverApplication {

    private final RoleServiceImplementation roleServiceImplementation;
    private final UserService userServiceImplementation;

    public static void main(String[] args) {
        SpringApplication.run(DriverApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createUser() {

        roleServiceImplementation.create(Role.builder().name(Role.Type.ROLE_USER).build());
        roleServiceImplementation.create(Role.builder().name(Role.Type.ROLE_ADMIN).build());

        userServiceImplementation.create(
                User.builder()
                        .email("pendrakar@gmail.com")
                        .password("user123")
                        .roles(Set.of(roleServiceImplementation.readRoleById(1L)))
                        .build()
        );

        userServiceImplementation.create(
                User.builder()
                        .email("jelinski93@gmail.com")
                        .password("admin123")
                        .roles(roleServiceImplementation.readAllRoles())
                        .build()
        );

    }

}
