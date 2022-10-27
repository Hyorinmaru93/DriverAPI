package pl.hyorinmaru.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hyorinmaru.driver.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
