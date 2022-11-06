package pl.hyorinmaru.driver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.repository.UserRepo;
import pl.hyorinmaru.driver.service.UserService;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepo repository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    public User readById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResolutionException(String.format("User with id %s was not found", id))
        );
    }

    @Override
    public User readByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new ResolutionException(String.format("User with email %s was not found", email))
        );
    }

    @Override
    public Optional<User> readByEmailOpt(String email){
        return repository.findByEmail(email);
    }

    @Override
    public List<User> readAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User update(User user) {
        if(user.getPassword() != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        readById(id);
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Cokolwiek");
        return readByEmail(username);
    }

    public Boolean isAlreadyRegistred(String email) {
        return repository.existsByEmail(email);
    }

    public boolean checkPassword(String password) {
        return !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?\\/*~$^+=<>]).{8,20}$");
    }

}
