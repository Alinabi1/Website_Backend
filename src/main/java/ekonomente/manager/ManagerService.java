package ekonomente.manager;

import ekonomente.consult.Consult;
import ekonomente.consult.ConsultRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ConsultRepository consultRepository;

    public ManagerService(ManagerRepository managerRepository, ConsultRepository consultRepository){
        this.managerRepository = managerRepository;
        this.consultRepository = consultRepository;
    }

    public List<ManagerDTO> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ManagerDTO convertToDTO(Manager manager) {
        List<String> consultantNames = manager.getConsults().stream()
                .map(Consult::getName)
                .collect(Collectors.toList());
        return new ManagerDTO(manager.getId(), manager.getName(), manager.getEmail(), consultantNames);
    }

    public List<Manager> getManagers(){
        return managerRepository.findAll();
    }

    public void addManager(Manager manager) {
        Optional<Manager> managerOptional = managerRepository.findManagerByEmail(manager.getEmail());

        if (managerOptional.isPresent()){
            throw new IllegalArgumentException("Email taken");
        }
        managerRepository.save(manager);
    }

    public void deleteManager(long managerId) {
        boolean idExists = managerRepository.existsById(managerId);

        if (!idExists){
            throw new IllegalArgumentException("Manager with id " + managerId + " does not exist");
        }

        managerRepository.deleteById(managerId);
    }

    @Transactional
    public void updateManager(long managerId, String name, String email) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Manager with id " + managerId + " does not exist"));

        if (name != null && !name.isEmpty()){
            manager.setName(name);
        }

        if (email != null && !email.isEmpty()){
            Optional<Manager> managerOptional = managerRepository.
                    findManagerByEmail(email);
            if (managerOptional.isPresent()){
                throw new IllegalArgumentException("Email taken");
            }
            manager.setEmail(email);
        }
    }

    @Transactional
    public void assignConsult(long managerId, long consultId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("No manager with id " + managerId + "exist!"));
        Consult consult = consultRepository.findById(consultId)
                .orElseThrow(()-> new IllegalArgumentException("No consult with id" + consultId + "exist!"));
        manager.addConsult(consult);
        managerRepository.save(manager);
    }

    @Transactional
    public void unassignConsult(long managerId, long consultId) {      // om både finns i sina tabell men inte har en relation?
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("No manager with id " + managerId + "exist!"));
        Consult consult = consultRepository.findById(consultId)
                .orElseThrow(()-> new IllegalArgumentException("No consult with id" + consultId + "exist!"));
        manager.removeConsult(consult);
        managerRepository.save(manager);
    }

    // Method to verify login credentials
    public boolean verifyLogin(String email, String password) {
        Optional<Manager> managerOptional = managerRepository.findManagerByEmailAndPassword(email, password);
        return managerOptional.isPresent();
    }

    public Long getManagerIdByEmail(String email) {
        return managerRepository.findManagerIdByEmail(email);
    }
}
