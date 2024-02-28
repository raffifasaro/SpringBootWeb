package spring.springdevbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.springdevbackend.discordBot.BotMain;

@SpringBootApplication
public class SpringDevBackendApplication {

    public static void main(String[] args) {
        BotMain bot = new BotMain();
        bot.buildBot();

        SpringApplication.run(SpringDevBackendApplication.class, args);
    }

}
