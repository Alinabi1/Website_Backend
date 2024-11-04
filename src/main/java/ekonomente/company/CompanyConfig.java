package ekonomente.company;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyConfig {

    @Bean
    CommandLineRunner commandLineRunnerCompany(CompanyRepository companyRepository) {
        return args -> {

        };
    }
}
