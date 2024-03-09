package spring.springdevbackend.discordBot;



import discord4j.common.util.Snowflake;
import discord4j.core.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.springdevbackend.eventModel.Event;
import spring.springdevbackend.repository.EventRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class BotDiscord {

    private final EventRepository repository;
    @Autowired
    public BotDiscord(EventRepository repository) {
        this.repository = repository;
    }

    private static final String TOKEN_FILE_PATH = "src/main/java/spring/springdevbackend/discordBot/token.txt";
    private static final String USER_ID_PATH = "src/main/java/spring/springdevbackend/discordBot/user.txt";

    private DiscordClient client = null;

    private String botSendMessage(DiscordClient client, String userID, String text) {
        if (client != null) {
            client.withGateway(gateway -> gateway.getUserById(Snowflake.of(userID)).flatMap(user -> user.getPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.createMessage(text))
                    .then())).subscribe();
            return "Message sent";
        }
        return "Sending failed";
    }

    private String getUserID(String idPath) {
        try {
            return Files.readString(Paths.get(idPath));
        } catch (IOException e) {
            return "Missing user.txt";
        }
    }

    private Iterable<Event> checkDB() {
        return repository.findAll();
    }

    private String cleanDB() {
        for (Event event : checkDB()) {
            String deleted = "";
            //Check if events are old and delete them
            if (LocalDateTime.of(event.getDate(), event.getTime()).isBefore(LocalDateTime.now())) {
                repository.delete(event);
                deleted = deleted + event + ", ";
            }
            return deleted;
        }
        return "";
    }

    @PostConstruct
    public String buildBot() {
        try {
            client = DiscordClient.create(Files.readString(Paths.get(TOKEN_FILE_PATH)));
            return "bot built";
        } catch (IOException e) {
            return "Missing token.txt File inside discordBot directory";
        }
    }

    public void botTest() {
        botSendMessage(client, getUserID(USER_ID_PATH), "Test");
    }

    @Scheduled(fixedRate = 120000)
    @Async
    public void eventListener() throws ParseException {
        for (Event event : checkDB()) {
            //Check Time
            if (LocalDateTime.of(event.getDate(), event.getTime()).isBefore(LocalDateTime.now())) {
                if (getUserID(USER_ID_PATH) != null) {
                    System.out.println("Bot:" + botSendMessage(client, getUserID(USER_ID_PATH), event.getText()));
                    System.out.println("Cleaned:" + cleanDB());
                }
            }
        }
    }
}