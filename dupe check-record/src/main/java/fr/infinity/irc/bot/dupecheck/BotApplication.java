package fr.infinity.irc.bot.dupecheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApplication {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BotApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }



}
