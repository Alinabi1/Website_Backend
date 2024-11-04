package ekonomente.consult;

import java.util.List;

public class ConsultDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> missions;
    public ConsultDTO(Long id, String name, String email, List<String> missions){
        this.id = id;
        this.name = name;
        this.email = email;
        this.missions = missions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMissions() {
        return missions;
    }

    public void setMissions(List<String> missions) {
        this.missions = missions;
    }
}
