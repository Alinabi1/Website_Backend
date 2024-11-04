package ekonomente.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ManagerConfig {
    @Bean
    CommandLineRunner commandLineRunnerManager(ManagerRepository managerRepository) {
        return args -> {
            Manager Messi = new Manager(1L,
                    "Messi",
                    "Messi1987@hotmail.com",
                    "10"
            );

            Manager Neymar = new Manager(
                    "Neymar",
                    "Neymar1994@hotmail.com",
                    "11"
            );

            managerRepository.saveAll(List.of(Messi, Neymar));
        };
    }

}
