package spring.springdevbackend.discordBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BotMain {

    private static final String TOKEN_FILE_PATH = "src/main/java/spring/springdevbackend/discordBot/token.txt";

    public String buildBot() {
        try {
            JDA bot = JDABuilder.createDefault(Files.readString(Paths.get(TOKEN_FILE_PATH))).build();
            return "bot built";
        } catch (IOException e) {
            return "Missing token.txt File inside discordBot directory";
        }
    }
}
