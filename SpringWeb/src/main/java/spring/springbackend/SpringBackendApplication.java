package spring.springbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "spring.springbackend.eventModel")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringBackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBackendApplication.class, args);
    }
}
