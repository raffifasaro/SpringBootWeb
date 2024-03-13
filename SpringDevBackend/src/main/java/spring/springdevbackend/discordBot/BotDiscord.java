package spring.springdevbackend.discordBot;


import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.springdevbackend.eventModel.Event;
import spring.springdevbackend.repository.EventRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class BotDiscord {

    private final EventRepository repository;

    @Autowired
    public BotDiscord(EventRepository repository) {
        this.repository = repository;
    }

    private static final String TOKEN_FILE_PATH = "src/main/java/spring/springdevbackend/discordBot/token.txt";
    private static final String USER_ID_PATH = "src/main/java/spring/springdevbackend/discordBot/user.txt";

    private static final String BACKUP_FILE_PATH = "";

    private DiscordClient client = null;

    private static final Logger LOG = LoggerFactory.getLogger(BotDiscord.class);

    private boolean botSendMessage(final DiscordClient client, final String userID, final String text) {
        if (client != null) {
            client.withGateway(gateway -> gateway.getUserById(Snowflake.of(userID)).flatMap(user -> user.getPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.createMessage(text))
                    .then())).subscribe();
            return true;
        }
        LOG.warn("DiscordClient was not initialized!");
        return false;
    }

    private Optional<String> getUserID(final String idPath) {
        try {
            return Optional.of(Files.readString(Paths.get(idPath)));
        } catch (IOException ignored) {
            LOG.warn("Missing user.txt");
            return Optional.empty();
        }
    }

    private Iterable<Event> checkDB() {
        return repository.findAll();
    }

    private String cleanDB() {
        for (Event event : checkDB()) {
            final StringBuilder stringBuilder = new StringBuilder();
            //Check if events are old and delete them
            if (LocalDateTime.of(event.getDate(), event.getTime()).isBefore(LocalDateTime.now())) {
                repository.delete(event);
                stringBuilder.append(event).append(", ");
            }
            return stringBuilder.toString();
        }
        return "";
    }

    @PostConstruct
    public boolean buildBot() {
        try {
            client = DiscordClient.create(Files.readString(Paths.get(TOKEN_FILE_PATH)));
            LOG.debug("bot built");
            return true;
        } catch (IOException ignored) {
            LOG.warn("Missing token.txt File inside discordBot directory");
            return false;
        }
    }

    public void botTest() {
        // check return value of getUserID!
        botSendMessage(client, getUserID(USER_ID_PATH).orElseThrow(), "Test");
    }

    @Scheduled(fixedRate = 120000)
    @Async
    public void eventListener() {
        for (Event event : checkDB()) {
            //Check Time
            if (LocalDateTime.of(event.getDate(), event.getTime()).isBefore(LocalDateTime.now())) {
                getUserID(USER_ID_PATH).ifPresent(userID -> {

                    // use the logging of the original methods and dont log them out here
                    if (botSendMessage(client, userID, event.getText())) {
                        LOG.info("Bot: Message sent");
                    } else {
                        LOG.info("Bot: Sending Message failed!");
                    }
                    LOG.info("Cleaned:" + cleanDB());
                });
            }
        }
    }

    //@Scheduled(fixedRate = 86400000)
    public void backupDB() {
        Iterable<Event> events = repository.findAll();
        events.forEach(event -> Event.serialise(event, BACKUP_FILE_PATH));
        LOG.info("Database backup complete");
    }

    public void getDbFromBackup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BACKUP_FILE_PATH));
            String serializedEvent;
            while ((serializedEvent = reader.readLine()) != null) {
                if (!serializedEvent.isEmpty()) {
                    Event.deSerialise(serializedEvent).ifPresent(repository::save);
                }
            }
        } catch (IOException e) {
            LOG.warn("Backup file read error");
        }
    }
}