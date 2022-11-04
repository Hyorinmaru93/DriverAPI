package pl.hyorinmaru.driver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.repository.RoleRepo;
import pl.hyorinmaru.driver.repository.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Transactional
    public void create(User user) {
        userRepo.save(user);
    }

    public Boolean isAlreadyRegistred(String email) {
        return userRepo.existsByEmail(email);
    }

}
