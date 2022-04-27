package fr.infinity.irc.bot.dupecheck.quarantine;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QuarantineService {

    private final QuarantineRepository quarantineRepository;

    @Autowired
    public QuarantineService(QuarantineRepository quarantineRepository) {
        this.quarantineRepository = quarantineRepository;
    }

    public void saveQuarantine(GenericMessageEvent event, String command, String error_msg, String serverAlias) {
        Quarantine newQuarantine = new Quarantine(event.getMessage(), error_msg, command, event.getUser().getNick(), ((MessageEvent) event).getChannel().getName(),
                serverAlias, new Timestamp(event.getTimestamp()));
        this.quarantineRepository.save(newQuarantine);
    }
}
