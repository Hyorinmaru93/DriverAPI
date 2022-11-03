package pl.hyorinmaru.driver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.repository.RoleRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;

    public void create(Role role){
        roleRepo.save(role);
    }

    public Role readRoleByName(String name){
        return roleRepo.findByName(name).orElse(roleRepo.findByName("USER").get());
    }

}
