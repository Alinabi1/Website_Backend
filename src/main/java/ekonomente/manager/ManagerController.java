package ekonomente.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    /*
    @GetMapping
    public List<Manager> getManagers() {
        return managerService.getManagers();
    }
     */

    @GetMapping("/allManagers")
    public List<Map<Long, String>> getManagers(){
        List<Manager> managers = managerService.getManagers();
        List<Map<Long, String>> managersWithIds = new ArrayList<>();
        for (Manager manager : managers) {
            Map<Long, String> managerMap = new HashMap<>();
            managerMap.put(manager.getId(), manager.toString());
            managersWithIds.add(managerMap);
        }
        return managersWithIds;
    }

    @GetMapping("/{managerId}/name")
    public String getManagerName(@PathVariable("managerId") long managerId){
        List<Manager> managers = managerService.getManagers();
        for (Manager manager : managers) {
            if (managerId == manager.getId()) {
                return manager.getName();
            }
        }
        return "Manager";
    }

    @GetMapping("/allManagersDTO")
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        List<ManagerDTO> dtos = managerService.getAllManagers();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping(path = "/register")
    public void registerManager(@RequestBody Manager manager){
        managerService.addManager(manager);
    }

    @DeleteMapping(path = "/{managerId}/delete")
    public void deleteManager(@PathVariable("managerId") long managerId){
        managerService.deleteManager(managerId);
    }

    @PutMapping(path = "/{managerId}/update")
    public void updateManager(
            @PathVariable("managerId") long managerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        managerService.updateManager(managerId, name, email);
    }


    @PutMapping(path = "/{managerId}/{consultId}/assignConsult")
    public void assignConsult(
            @PathVariable("managerId") long managerId,
            @PathVariable("consultId") long consultId) {
        managerService.assignConsult(managerId,consultId);
    }

    @DeleteMapping(path = "/{managerId}/{consultId}/unassignConsult")
    public void unassignConsult(
            @PathVariable("managerId") long managerId,
            @PathVariable("consultId") long consultId) {
        managerService.unassignConsult(managerId,consultId);
    }

    @PostMapping("/login")
    public ResponseEntity<ManagerController.LoginResponse> login(@RequestBody LoginRequest request) {
        boolean loginSuccessful = managerService.verifyLogin(request.email, request.password);
        if (loginSuccessful) {
            Long managerId = managerService.getManagerIdByEmail(request.email); // Assuming you have a method to retrieve the ID
            return ResponseEntity.ok(new ManagerController.LoginResponse("Login successful", managerId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ManagerController.LoginResponse("Invalid email or password", null));
        }
    }

    public static class LoginResponse {
        private String message;
        private Long managerId;

        public LoginResponse(String message, Long managerId) {
            this.message = message;
            this.managerId = managerId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getManagerId() {
            return managerId;
        }

        public void setManagerId(Long managerId) {
            this.managerId = managerId;
        }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
