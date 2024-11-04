package ekonomente.timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimestampConfig {
    @Bean
    CommandLineRunner commandLineRunnerTimestamp(TimestampRepository timestampRepository) {
        return args -> {

        };
    }
}
