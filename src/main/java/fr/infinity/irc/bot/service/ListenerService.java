package fr.infinity.irc.bot.service;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static fr.infinity.irc.bot.service.ManagerSingleton.*;

@Service
public class ListenerService extends ListenerAdapter {

    private final ListenerInputService listenerInputService;
    private final ListenerOutputService listenerOutputService;

    @Autowired
    public ListenerService(ListenerInputService listenerInputService, ListenerOutputService listenerOutputService) {
        this.listenerInputService = listenerInputService;
        this.listenerOutputService = listenerOutputService;
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) {

        if (getChannelsListOutputManager().contains(((MessageEvent) event).getChannel().getName())) {
            this.listenerOutputService.executeOutput(event);
        } else if (getChannelsListInputManager().contains(((MessageEvent) event).getChannel().getName())) {
            this.listenerInputService.executeOutput(event);
        }

    }
}
