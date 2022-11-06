package pl.hyorinmaru.driver.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceImplementation implements UserService {

    private final UserRepo repository;

    @Override
    @Transactional
    public void create(User user) {
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
    public List<User> readAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User update(User user) {
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
        return null;
    }

    public Boolean isAlreadyRegistred(String email) {
        return repository.existsByEmail(email);
    }

    public boolean checkPassword(String password) {
        return !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?\\/*~$^+=<>]).{8,20}$");
    }


}
