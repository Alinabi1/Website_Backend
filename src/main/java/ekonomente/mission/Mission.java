package ekonomente.mission;

import ekonomente.company.Company;
import ekonomente.consult.Consult;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Mission")
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    private Long id;
    @Column(name = "name",nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "start_date",nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(name = "end_date",nullable = false, columnDefinition = "DATE")
    private LocalDate endDate;
    @ManyToMany(mappedBy = "missions")
    private List<Consult> consults;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Mission(Long id, String name, LocalDate startDate, LocalDate endDate, Company company) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.consults = new ArrayList<>();
        this.company = company;
    }

    public Mission(String name, LocalDate startDate, LocalDate endDate, Company company) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.consults = new ArrayList<>();
        this.company = company;
    }

    public Mission() {}

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Consult> getConsults() {
        return new ArrayList<>(consults);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void addConsult(Consult consult) {
        if (!consults.contains(consult)) {
            consults.add(consult);
            consult.addMission(this);
        }
        else throw new IllegalArgumentException("Consult already exists");
    }

    public void removeConsult(Consult consult) {
        if (consults.contains(consult)) {
            consults.remove(consult);
            consult.removeMission(this);
        }
        else throw new IllegalArgumentException("Consult does not exist");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return Objects.equals(name, mission.name) &&
                Objects.equals(company, mission.company) &&
                Objects.equals(startDate, mission.startDate) &&
                Objects.equals(endDate, mission.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, startDate, endDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", ").append(startDate);
        sb.append(" - ").append(endDate);
        sb.append(", consultants: [");
        for (Consult consult : consults) {
            sb.append(consult.getName()).append(", ");
        }
        sb.append("]");
        sb.append(", company: ").append(company.getName());
        return sb.toString();
    }
}
