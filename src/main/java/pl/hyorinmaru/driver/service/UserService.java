package pl.hyorinmaru.driver.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.hyorinmaru.driver.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void create(User user);

    User readById(Long id);

    User readByEmail(String email);

    Optional<User> readByEmailOpt(String email);

    List<User> readAll();

    User update(User user);

    void deleteById(Long id);
}
