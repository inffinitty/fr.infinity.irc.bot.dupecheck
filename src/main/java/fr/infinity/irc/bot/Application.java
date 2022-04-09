package fr.infinity.irc.bot;

import fr.infinity.irc.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Application implements CommandLineRunner {


    private static BotService botService;

    @Autowired
    public Application(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void run(String... args) throws Exception {
        execute(args);
    }

    public static void execute(String[] args) {
        botService.execute();
    }
}
