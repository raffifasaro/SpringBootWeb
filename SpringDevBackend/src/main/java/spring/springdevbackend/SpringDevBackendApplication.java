package spring.springdevbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EntityScan(basePackages = "spring.springdevbackend.eventModel")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringDevBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDevBackendApplication.class, args);
    }
}
