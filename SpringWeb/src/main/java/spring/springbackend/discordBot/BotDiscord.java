package spring.springbackend.discordBot;


import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.springbackend.eventModel.Event;
import spring.springbackend.repository.EventRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BotDiscord {

    private final EventRepository repository;

    @Autowired
    public BotDiscord(EventRepository repository) {
        this.repository = repository;
    }

    @Value("${token}")
    private String TOKEN;

    @Value("${user.id}")
    private String USER_ID;

    @Value("${backup.path}")
    private String BACKUP_FILE_PATH;

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

    private Optional<String> getUserID() {
        return Optional.of(USER_ID);
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
        if (TOKEN.length() > 50) {
            client = DiscordClient.create(TOKEN);
            LOG.debug("bot built");
            return true;
        } else {
            LOG.warn("Invalid token");
            return false;
        }
    }

    public void botTest() {
        // check return value of getUserID!
        botSendMessage(client, getUserID().orElseThrow(), "Test");
    }

    @Scheduled(fixedRate = 60000)
    @Async
    public void eventListener() {
        if (client != null) {
            for (Event event : checkDB()) {
                //Check Time
                if (LocalDateTime.of(event.getDate(), event.getTime()).isBefore(LocalDateTime.now())) {
                    getUserID().ifPresent(userID -> {

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
        } else LOG.warn("client is null");
    }

    // 1 day in ms = 86400000
    @Scheduled(fixedRate = 60000)
    public void backupDB() {
        Iterable<Event> events = repository.findAll();
        List<Event> eventList = new ArrayList<>();
        events.forEach(eventList::add);

        events.forEach(event -> Event.serialise(eventList, BACKUP_FILE_PATH));
        LOG.info("Database backup complete");
    }

    @PostConstruct
    public void getDbFromBackup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BACKUP_FILE_PATH));
            String serializedEvents;
            while ((serializedEvents = reader.readLine()) != null) {
                if (!serializedEvents.isEmpty()) {
                    Event.deSerialise(serializedEvents).ifPresent(events -> events.forEach(event -> {
                        if (!repository.existsById(event.getId())) {
                            repository.save(event);
                        }
                    }));
                }
            }
        } catch (IOException e) {
            LOG.warn("Backup file read error");
        }
    }
}