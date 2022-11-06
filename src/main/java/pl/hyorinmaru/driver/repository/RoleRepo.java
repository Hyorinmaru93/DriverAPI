package pl.hyorinmaru.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hyorinmaru.driver.model.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);


}
