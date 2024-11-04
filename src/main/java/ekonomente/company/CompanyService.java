package ekonomente.company;

import ekonomente.manager.Manager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public void addCompany(Company company) {
        Optional<Company> existingCompany = companyRepository.findIdenticCompany(company.getName(), company.getLocation());

        if (existingCompany.isPresent()) {
            throw new IllegalArgumentException("Identic company already exists");
        }

        companyRepository.save(company);
    }

    public void deleteCompany(long companyId) {
        boolean idExists = companyRepository.existsById(companyId);

        if (!idExists){
            throw new IllegalArgumentException("Company with id " + companyId + " does not exist");
        }

        companyRepository.deleteById(companyId);
    }

    @Transactional
    public void updateCompany(long companyId, String name, String location, Manager manager) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Company with id " + companyId + " does not exist"));

        if (name != null && !name.isEmpty()){
            company.setName(name);
        }

        if (location != null && !location.isEmpty()){
            company.setLocation(location);
        }

        if (manager != null){
            company.setManager(manager);
        }
    }
}
