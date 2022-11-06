package pl.hyorinmaru.driver.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pl.hyorinmaru.driver.model.Role;
import pl.hyorinmaru.driver.repository.RoleRepo;
import pl.hyorinmaru.driver.service.RoleService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {

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

    public Set<Role> readAllRoles(){
        return new HashSet<>(roleRepo.findAll());
    }
}
