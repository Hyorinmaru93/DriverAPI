package pl.hyorinmaru.driver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
        return roleRepo.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Role with name %s was not found", name)));
    }

    public Role readRoleById(Long id){
        return roleRepo.findById(id).get();
    }

}
