package ekonomente.manager;

import ekonomente.company.Company;
import ekonomente.consult.Consult;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Manager")
@Table(name = "manager")
public class Manager {
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
    @ManyToMany(mappedBy = "managers")
    private List<Consult> consults;
    @OneToMany(mappedBy = "manager")
    private List<Company> companies;

    public Manager(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.consults = new ArrayList<>();
        this.companies = new ArrayList<>();
    }

    public Manager(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.consults = new ArrayList<>();
        this.companies = new ArrayList<>();
    }

    public Manager() {}

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

    public List<Consult> getConsults() {
        return new ArrayList<>(consults);
    }

    public List<Company> getCompanies() {
        return new ArrayList<>(companies);
    }

    public void addConsult(Consult consult) {
        if (!consults.contains(consult)) {
            consult.addManager(this);
            consults.add(consult);
        }
        else throw new IllegalArgumentException("Consult already exists");
    }

    public void removeConsult(Consult consult) {
        if (consults.contains(consult)) {
            consults.remove(consult);
            consult.removeManager(this);
        }
        else throw new IllegalArgumentException("Consult does not exist");
    }

    public void addCompany(Company company) {
        if (!companies.contains(company)) {
            companies.add(company);
            company.setManager(this);
        }
        else throw new IllegalArgumentException("Company already exists");
    }

    public void removeCompany(Company company) {
        if (companies.contains(company)) {
            companies.remove(company);
            company.setManager(null);
        }
        else throw new IllegalArgumentException("Company does not exist");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(name, manager.name) && Objects.equals(email, manager.email);
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
        sb.append(", consults: [");
        for (Consult consult : consults) {
            sb.append(consult.getName()).append(", ");
        }
        sb.append("]");
        sb.append(", companies: [");
        for (Company company : companies) {
            sb.append(company.getName()).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
