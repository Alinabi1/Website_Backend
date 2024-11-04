package ekonomente.consult;

import ekonomente.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult,Long> {
    @Query("SELECT consult FROM Consult consult WHERE consult.email = ?1")
    Optional<Consult> findConsultByEmail(String email);

    @Query("SELECT consult FROM Consult consult WHERE consult.email = ?1 and consult.password = ?2")
    Optional<Consult> findConsultByEmailAndPassword(String email, String password);

    @Query("SELECT mission FROM Consult consult JOIN consult.missions mission WHERE consult.id = :consultId")
    List<Mission> findMissionsByConsultId(Long consultId);

    @Query("SELECT consult.id FROM Consult consult WHERE consult.email = ?1")
    Long findConsultIdByEmail(String email);
}
