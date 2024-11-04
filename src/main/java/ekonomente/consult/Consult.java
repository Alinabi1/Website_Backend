package ekonomente.consult;

import ekonomente.manager.Manager;
import ekonomente.mission.Mission;
import ekonomente.timestamp.Timestamp;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Consult")
@Table(name = "consult")
public class Consult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;
    @Column(name = "name",nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "email",nullable = false, columnDefinition = "VARCHAR(320)", unique = true)
    private String email;
    @Column(name = "password",nullable = false, columnDefinition = "TEXT")
    private String password;
    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL)
    private List<Timestamp> timestamps;
    @ManyToMany
    @JoinTable(
            name = "consult_manager",
            joinColumns = @JoinColumn(name = "consult_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private List<Manager> managers;
    @ManyToMany
    @JoinTable(
            name = "consult_mission",
            joinColumns = @JoinColumn(name = "consult_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    private List<Mission> missions;

    public Consult(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.timestamps = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.missions = new ArrayList<>();
    }

    public Consult(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.timestamps = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.missions = new ArrayList<>();
    }

    public Consult() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Timestamp> getTimestamps() {
        return new ArrayList<>(timestamps);
    }

    public List<Manager> getManagers() {
        return new ArrayList<>(managers);
    }

    public List<Mission> getMissions() {
        return new ArrayList<>(missions);
    }

    public void addTimestamp(Timestamp timestamp) {
        if (!timestamps.contains(timestamp)){
            timestamps.add(timestamp);
            timestamp.setConsult(this);
        }
        else throw new IllegalArgumentException("Timestamp already exists");
    }

    public void addManager(Manager manager) {
        if (!managers.contains(manager)){
            managers.add(manager);
        }
        else throw new IllegalArgumentException("Manager already exists");
    }
    public void addMission(Mission mission) {
        if (!missions.contains(mission)){
            missions.add(mission);
        }
        else throw new IllegalArgumentException("Mission already exists");
    }

    public void removeManager(Manager manager) {
        if (managers.contains(manager)){
            managers.remove(manager);
        }
        else throw new IllegalArgumentException("Manager does not exist");
    }

    public void removeMission(Mission mission) {
        if (missions.contains(mission)){
            missions.remove(mission);
        }
        else throw new IllegalArgumentException("Mission does not exist");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consult consult = (Consult) o;
        return Objects.equals(name, consult.name) && Objects.equals(email, consult.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", email: ").append(email);
        sb.append(", timestamps: [");
        for (Timestamp timestamp : timestamps) {
            sb.append(timestamp.getNotes()).append(", ");
        }
        sb.append("]");
        sb.append(", managers: [");
        for (Manager manager : managers) {
            sb.append(manager.getName()).append(", ");
        }
        sb.append("]");
        sb.append(", missions: [");
        for (Mission mission : missions) {
            sb.append(mission.getName()).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
