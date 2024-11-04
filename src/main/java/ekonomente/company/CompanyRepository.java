package ekonomente.company;

import ekonomente.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("SELECT company FROM Company company WHERE company.name = ?1")
    Optional<Company> findCompanyByName(String name);

    @Query("SELECT company FROM Company company WHERE company.location = ?1")
    Optional<Company> findCompanyByLocation(String location);

    @Query("SELECT company FROM Company company WHERE company.manager = ?1")
    Optional<Company> findCompanyByManager(Manager manager);

    @Query("SELECT company FROM Company company WHERE company.name = ?1 AND company.location = ?2")
    Optional<Company> findIdenticCompany(String name, String location);
}
