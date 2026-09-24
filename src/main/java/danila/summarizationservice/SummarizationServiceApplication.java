package danila.summarizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SummarizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummarizationServiceApplication.class, args);
    }

}
