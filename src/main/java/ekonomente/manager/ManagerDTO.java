package ekonomente.manager;

import java.util.List;

public class ManagerDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> consultants;
    public ManagerDTO(Long id, String name, String email, List<String> consultants) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.consultants = consultants;
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

    public List<String> getConsultants() {
        return consultants;
    }

    public void setConsultants(List<String> consultants) {
        this.consultants = consultants;
    }
}
