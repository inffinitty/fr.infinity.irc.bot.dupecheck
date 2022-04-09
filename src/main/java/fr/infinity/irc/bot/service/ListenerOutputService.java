package fr.infinity.irc.bot.service;

import fr.infinity.irc.bot.release.Release;
import fr.infinity.irc.bot.release.ReleaseService;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.Month;
import java.util.List;
import java.util.Locale;

@Service
public class ListenerOutputService {

    private ReleaseService releaseService;

    @Autowired
    public ListenerOutputService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    public void executeOutput(GenericMessageEvent event) {
        String command = event.getMessage();
        int idBot = event.getBot().getBotId();
        String channelBot = ((MessageEvent) event).getChannel().getName();

        String comAction = "";
        String comQuery = "";
        int comParam = 10;

        if ("!".equals(command.substring(0,1))) {

            String[] commandParsed = command.split(" ");

            comAction = commandParsed[0];
            if (commandParsed.length>1) {
                comQuery = commandParsed[1];
            }
            if (commandParsed.length>2) {
                comParam = Integer.valueOf(commandParsed[2]);
                if (comParam > 25) {
                    comParam = 25;
                }
            }
          
            if ("!dupe".equals(comAction)) {
              this.dupe(idBot, channelBot, comQuery, comParam);
            }
        }
    }

    private void dupe(int idBot, String channelBot, String comQuery, int comParam) {
        List<Release> dupeList = releaseService.dupe(comQuery, comParam);
        dupeList.forEach(release -> {
            ManagerSingleton.getManager().getBotById(idBot).send().message(channelBot, release.displayInfo());
        });

    }


}
