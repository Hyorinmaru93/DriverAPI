package pl.hyorinmaru.driver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.model.User;
import pl.hyorinmaru.driver.repository.RoleRepo;
import pl.hyorinmaru.driver.repository.UserRepo;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    public void create(User user){
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        user.setRoles(new HashSet<>((Arrays.asList(userRole))));
        userRepo.save(user);
    }

}
