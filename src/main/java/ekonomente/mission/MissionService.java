package ekonomente.mission;

import ekonomente.consult.Consult;
import ekonomente.consult.ConsultRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final ConsultRepository consultRepository;

    public MissionService(MissionRepository missionRepository, ConsultRepository consultRepository){
        this.missionRepository = missionRepository;
        this.consultRepository = consultRepository;
    }

    public List<Mission> getMissions(){
        return missionRepository.findAll();
    }

    public void addMission(Mission mission) {
        Optional<Mission> existingMission = missionRepository.findIdenticMission(
                mission.getCompany(), mission.getName(), mission.getStartDate(), mission.getEndDate());

        if (existingMission.isPresent()) {
            throw new IllegalArgumentException("Identic mission already exists");
        }

        missionRepository.save(mission);
    }

    public void deleteMission(long missionId) {
        boolean idExists = missionRepository.existsById(missionId);

        if (!idExists){
            throw new IllegalArgumentException("Mission with id " + missionId + " does not exist");
        }

        missionRepository.deleteById(missionId);
    }

    @Transactional
    public void updateMission(long missionId, String name, LocalDate startDate, LocalDate endDate) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mission with id " + missionId + " does not exist"));

        if (name != null && !name.isEmpty()){
            mission.setName(name);
        }

        if (startDate != null && endDate != null && startDate.isBefore(endDate)){
            mission.setStartDate(startDate);
            mission.setEndDate(endDate);
        }
    }

    @Transactional
    public void assignConsult(long missionId,long consultId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(()-> new IllegalArgumentException("No mission with id " + missionId + " exist!"));
        Consult consult = consultRepository.findById(consultId)
                .orElseThrow(()-> new IllegalArgumentException("No consult with id " + consultId + " exist!"));
        mission.addConsult(consult);
        missionRepository.save(mission);
    }

    @Transactional
    public void unassignConsult(long missionId, long consultId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(()-> new IllegalArgumentException("No mission with id " + missionId + " exist!"));
        Consult consult = consultRepository.findById(consultId)
                .orElseThrow(()-> new IllegalArgumentException("No consult with id " + consultId + " exist!"));
        mission.removeConsult(consult);
        missionRepository.save(mission);
    }
}
