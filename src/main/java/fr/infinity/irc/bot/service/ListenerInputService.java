package fr.infinity.irc.bot.service;

import fr.infinity.irc.bot.release.Release;
import fr.infinity.irc.bot.release.ReleaseService;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ListenerInputService {

    ReleaseService releaseService;

    @Autowired
    public ListenerInputService(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    public void executeOutput(GenericMessageEvent event) {

        String[] parsedString = event.getMessage().split(" ");

        String command = parsedString[0];

        if ("!sitepre".equals(command)) {
          sitepre(parsedString, event);
        }

    }

    private void sitepre(String[] parsedString, GenericMessageEvent event) {
        String release = parsedString[1];
        String category = parsedString[2];

        String team = "";
        String patternStr = "-[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(release);
        if(matcher.find()){
            team = release.substring(matcher.start()+1);
        }
        
        Timestamp timestamp = new Timestamp(event.getTimestamp());
        Release newRelease = new Release(release, team, category, null, null, null, null, "mySource", timestamp, timestamp);
        releaseService.saveSitePre(newRelease);

    }

}
