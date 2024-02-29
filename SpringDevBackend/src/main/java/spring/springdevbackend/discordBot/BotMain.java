package spring.springdevbackend.discordBot;



import discord4j.core.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotMain {

    private static final String TOKEN_FILE_PATH = "src/main/java/spring/springdevbackend/discordBot/token.txt";

    public String buildBot() {
        try {
            DiscordClient.create(Files.readString(Paths.get(TOKEN_FILE_PATH)))
                    .withGateway(client ->
                            client.on(MessageCreateEvent.class, event -> {
                                Message message = event.getMessage();

                                if (message.getContent().equalsIgnoreCase("!ping")) {
                                    return message.getChannel()
                                            .flatMap(channel -> channel.createMessage("Pong!"));
                                }

                                return Mono.empty();
                            }))
                    .block();
            return "bot built";
        } catch (IOException e) {
            return "Missing token.txt File inside discordBot directory";
        }
    }
}
