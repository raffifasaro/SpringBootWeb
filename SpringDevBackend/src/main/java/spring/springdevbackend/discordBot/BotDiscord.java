package spring.springdevbackend.discordBot;



import discord4j.common.util.Snowflake;
import discord4j.core.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class BotDiscord {

    private static final String TOKEN_FILE_PATH = "src/main/java/spring/springdevbackend/discordBot/token.txt";
    private static final String USER_ID_PATH = "src/main/java/spring/springdevbackend/discordBot/user.txt";

    private static String userID = "";

    private final String dateString = "2024-02-04";
    private DiscordClient client = null;


    @PostConstruct
    public String buildBot() {
        try {
            client = DiscordClient.create(Files.readString(Paths.get(TOKEN_FILE_PATH)));
            return "bot built";
        } catch (IOException e) {
            return "Missing token.txt File inside discordBot directory";
        }
    }

    @Scheduled(fixedRate = 120000)
    @Async
    public void eventListener() throws ParseException, IOException {
        System.out.println("Hallo");
        userID = Files.readString(Paths.get(USER_ID_PATH));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        if (date.before(Calendar.getInstance().getTime())) {
            client.withGateway(gateway -> gateway.getUserById(Snowflake.of(userID)).flatMap(user -> user.getPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.createMessage("EVENT!"))
                    .then())).block();
        }
    }
}