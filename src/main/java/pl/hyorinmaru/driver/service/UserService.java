package pl.hyorinmaru.driver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.repository.RoleRepo;
import pl.hyorinmaru.driver.repository.UserRepo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Transactional
    public void create(User user){
        userRepo.save(user);
    }

}
