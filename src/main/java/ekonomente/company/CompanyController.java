package ekonomente.company;

import ekonomente.consult.Consult;
import ekonomente.manager.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /*
    @GetMapping
    public List<Company> getCompanies() {return companyService.getCompanies();}
     */

    @GetMapping(path = "/allCompanies")
    public List<Map<Long, String>> getConsults(){
        List<Company> companies = companyService.getCompanies();
        List<Map<Long, String>> companiesWithIds = new ArrayList<>();
        for (Company company : companies) {
            Map<Long, String> companyMap = new HashMap<>();
            companyMap.put(company.getId(), company.toString());
            companiesWithIds.add(companyMap);
        }
        return companiesWithIds;
    }

    @PostMapping(path = "/register")
    public void registerCompany(@RequestBody Company company){
        companyService.addCompany(company);
    }

    @DeleteMapping(path = "/{companyId}/delete")
    public void deleteCompany(@PathVariable("companyId") long companyId){
        companyService.deleteCompany(companyId);
    }

    @PutMapping(path = "/{companyId}/update")
    public void updateCompany(
            @PathVariable("companyId") long companyId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false)Manager manager){
        companyService.updateCompany(companyId,name,location,manager);
    }
}
