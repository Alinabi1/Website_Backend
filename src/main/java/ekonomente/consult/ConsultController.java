package ekonomente.consult;

import ekonomente.manager.Manager;
import ekonomente.mission.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "consult")
public class ConsultController {

    private final ConsultService consultService;

    @Autowired
    public ConsultController(ConsultService consultService) {
        this.consultService = consultService;
    }

    /*
    @GetMapping
    public List<Consult> getConsults() {return consultService.getConsults();}
    */

    @GetMapping("/allConsultants")
    public List<Map<Long, String>> getConsults(){
        List<Consult> consults = consultService.getConsults();
        List<Map<Long, String>> consultsWithIds = new ArrayList<>();
        for (Consult consult : consults) {
            Map<Long, String> consultMap = new HashMap<>();
            consultMap.put(consult.getId(), consult.toString());
            consultsWithIds.add(consultMap);
        }
        return consultsWithIds;
    }

    @GetMapping("/{consultId}/name")
    public String getConsultName(@PathVariable("consultId") long consultId){
        List<Consult> consults = consultService.getConsults();
        for (Consult consult : consults) {
            if (consultId == consult.getId()) {
                return consult.getName();
            }
        }
        return "Consult";
    }

    @GetMapping("/allConsultantsDTO")
    public ResponseEntity<List<ConsultDTO>> getAllConsultants() {
        List<ConsultDTO> dtos = consultService.getAllConsults();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(path = "/login/{consultId}")
    public ResponseEntity<List<Mission>> getMissions(@PathVariable Long consultId) {
        List<Mission> missions = consultService.getMissions(consultId);
        return ResponseEntity.ok(missions);
    }


    @PostMapping(path = "/register")
    public void registerConsult(@RequestBody Consult consult){
        consultService.addConsult(consult);
    }

    @DeleteMapping(path = "/{consultId}/delete")
    public void deleteConsult(@PathVariable("consultId") long consultId){
        consultService.deleteConsult(consultId);
    }

    @PutMapping(path = "/{consultId}/update")
    public void updateConsult(
            @PathVariable("consultId") long consultId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        consultService.updateConsult(consultId,name,email);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        boolean loginSuccessful = consultService.verifyLogin(request.email, request.password);
        if (loginSuccessful) {
            Long consultId = consultService.getConsultIdByEmail(request.email); // Assuming you have a method to retrieve the ID
            return ResponseEntity.ok(new LoginResponse("Login successful", consultId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password", null));
        }
    }

    public static class LoginResponse {
        private String message;
        private Long consultId;

        public LoginResponse(String message, Long consultId) {
            this.message = message;
            this.consultId = consultId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getConsultId() {
            return consultId;
        }

        public void setConsultId(Long consultId) {
            this.consultId = consultId;
        }

        // Getters and setters
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