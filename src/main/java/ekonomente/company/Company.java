package ekonomente.company;

import ekonomente.manager.Manager;
import ekonomente.mission.Mission;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Company")
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "location", nullable = false, columnDefinition = "TEXT")
    private String location;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @OneToMany(mappedBy = "company")
    private List<Mission> missions;

    public Company(Long id, String name, String location, Manager manager) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.manager = manager;
        this.missions = new ArrayList<>();
    }

    public Company(String name, String location, Manager manager) {
        this.name = name;
        this.location = location;
        this.manager = manager;
        this.missions = new ArrayList<>();
    }

    public Company() {}

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Mission> getMissions() {
        return new ArrayList<>(missions);
    }

    public void addMission(Mission mission) {
        if (!missions.contains(mission)) {
            missions.add(mission);
            mission.setCompany(this);
        }
        else throw new IllegalArgumentException("Mission already exists");
    }

    public void removeMission(Mission mission) {
        if (missions.contains(mission)) {
            missions.remove(mission);
            mission.setCompany(null);
        }
        else throw new IllegalArgumentException("Mission does not exist");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(location, company.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(", ").append(location)
                .append(", manager: ").append(manager.getName());

        sb.append(", missions: [");
        for (Mission mission : missions) {
            sb.append(mission.getName()).append(", ");
        }
        sb.append("]");

        return sb.toString();
    }
}
