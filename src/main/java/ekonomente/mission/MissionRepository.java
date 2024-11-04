package ekonomente.mission;

import ekonomente.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission,Long> {

    @Query("SELECT mission FROM Mission mission WHERE mission.company = ?1")
    Optional<Company> findMissionByCompany(Company company);

    @Query("SELECT mission FROM Mission mission WHERE mission.company = ?1 AND mission.name = ?2 AND mission.startDate = ?3 AND mission.endDate = ?4")
    Optional<Mission> findIdenticMission(Company company, String name, LocalDate startDate, LocalDate endDate);
}
