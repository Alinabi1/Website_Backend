package ekonomente.timestamp;

import ekonomente.consult.Consult;
import ekonomente.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface TimestampRepository extends JpaRepository<Timestamp,Long> {

    @Query("SELECT timestamp FROM Timestamp timestamp WHERE timestamp.mission = ?1")
    Optional<Mission> findTimestampByMission(Mission mission);

    @Query("SELECT timestamp FROM Timestamp timestamp WHERE timestamp.consult = ?1")
    Optional<Consult> findTimestampByConsult(Consult consult);

    @Query("SELECT timestamp FROM Timestamp timestamp WHERE timestamp.date = ?1")
    Optional<LocalDate> findTimestampByDate(Mission mission);

    @Query("SELECT timestamp FROM Timestamp timestamp WHERE timestamp.startTime = ?1 AND timestamp.endTime = ?2 AND timestamp.date = ?3 AND timestamp.consult = ?4 AND timestamp.mission = ?5")
    Optional<Timestamp> findIdenticTimestamp(LocalTime startTime, LocalTime endTime, LocalDate date, Consult consult, Mission mission);

}
