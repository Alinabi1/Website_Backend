package ekonomente.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    @Query("SELECT manager FROM Manager manager WHERE manager.email = ?1")
    Optional<Manager> findManagerByEmail(String email);

    @Query("SELECT manager FROM Manager manager WHERE manager.email = ?1 and manager.password = ?2")
    Optional<Manager> findManagerByEmailAndPassword(String email, String password);

    @Query("SELECT manager.id FROM Manager manager WHERE manager.email = ?1")
    Long findManagerIdByEmail(String email);
}