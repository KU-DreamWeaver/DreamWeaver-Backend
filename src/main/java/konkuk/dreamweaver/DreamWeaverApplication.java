package konkuk.dreamweaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class DreamWeaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamWeaverApplication.class, args);
    }

}
