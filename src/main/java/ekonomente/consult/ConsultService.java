package ekonomente.consult;

import ekonomente.mission.Mission;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultService {

    private final ConsultRepository consultRepository;

    @Autowired
    public ConsultService(ConsultRepository consultRepository){
        this.consultRepository = consultRepository;
    }

    public List<ConsultDTO> getAllConsults() {
        List<Consult> consults = consultRepository.findAll();
        return consults.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ConsultDTO convertToDTO(Consult consult) {
        List<String> missions = consult.getMissions().stream().
                map(Mission::getName).
                collect(Collectors.toList());
        return new ConsultDTO(consult.getId(), consult.getName(), consult.getEmail(), missions);
    }

    public List<Consult> getConsults(){
        return consultRepository.findAll();
    }

    public List<Mission> getMissions(Long consultId){
        return consultRepository.findMissionsByConsultId(consultId);
    }

    public void addConsult(Consult consult) {
        Optional<Consult> consultOptional = consultRepository.findConsultByEmail(consult.getEmail());

        if (consultOptional.isPresent()){
            throw new IllegalArgumentException("Email taken");
        }

        consultRepository.save(consult);
    }

    public void deleteConsult(long consultId) {
        boolean idExists = consultRepository.existsById(consultId);

        if (!idExists){
            throw new IllegalArgumentException("Consult with id " + consultId + " does not exist");
        }

        consultRepository.deleteById(consultId);
    }

    @Transactional
    public void updateConsult(long consultId, String name, String email) {
        Consult consult = consultRepository.findById(consultId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Consult with id " + consultId + " does not exist"));

        if (name != null && !name.isEmpty()){
            consult.setName(name);
        }

        if (email != null && !email.isEmpty()){
            Optional<Consult> consultOptional = consultRepository.
                    findConsultByEmail(email);
            if (consultOptional.isPresent()){
                throw new IllegalArgumentException("Email taken");
            }
            consult.setEmail(email);
        }
    }

    // Method to verify login credentials
    public boolean verifyLogin(String email, String password) {
        Optional<Consult> consultOptional = consultRepository.findConsultByEmailAndPassword(email, password);
        return consultOptional.isPresent();
    }

    public Long getConsultIdByEmail(String email) {
        return consultRepository.findConsultIdByEmail(email);
    }

}
