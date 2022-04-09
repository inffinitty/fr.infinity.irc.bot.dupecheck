package fr.infinity.irc.bot.oracle;

import fr.infinity.irc.bot.oracle.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BotApplication {


    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }



}
