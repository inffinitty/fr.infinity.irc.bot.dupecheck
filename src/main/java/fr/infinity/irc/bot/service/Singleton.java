package fr.infinity.irc.bot.service;

import org.pircbotx.MultiBotManager;

import java.util.ArrayList;
import java.util.List;

public class ManagerSingleton {

    private ManagerSingleton() {
    }

    private final static MultiBotManager manager = new MultiBotManager();

    private final static List<String> listChannels = new ArrayList<>();

    private final static List<String> listChannelsInput = new ArrayList<>();

    private final static List<String> listChannelsOutput = new ArrayList<>();

    public static synchronized MultiBotManager getManager() {
        return manager;
    }

    public static synchronized List<String> getChannelsListManager() {
        return listChannels;
    }

    public static synchronized List<String> getChannelsListInputManager() {
        return listChannelsInput;
    }

    public static synchronized List<String> getChannelsListOutputManager() {
        return listChannelsOutput;
    }

}
