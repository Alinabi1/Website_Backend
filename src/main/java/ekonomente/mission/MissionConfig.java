package ekonomente.mission;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MissionConfig {

    @Bean
    CommandLineRunner commandLineRunnerMission(MissionRepository missionRepository) {
        return args -> {

        };
    }

}
