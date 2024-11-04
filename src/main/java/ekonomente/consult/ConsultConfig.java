package ekonomente.consult;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ConsultConfig {

    @Bean
    CommandLineRunner commandLineRunnerConsult(ConsultRepository consultRepository) {
        return args -> {
            Consult Alan = new Consult(1L,
                    "Alan",
                    "Alan2003@hotmail.com",
                    "123"
            );

            Consult Josef = new Consult( //Behöver ej ange id här pga autoincrement
                    "Josef",
                    "Josef2000@hotmail.com",
                    "456"
            );

            consultRepository.saveAll(List.of(Alan, Josef));
        };
    }
}
