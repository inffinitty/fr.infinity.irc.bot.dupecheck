package fr.infinity.irc.bot.dupecheck.service;

import org.pircbotx.MultiBotManager;

import java.util.ArrayList;
import java.util.List;

public class SingletonFactory {

    private SingletonFactory() {
    }

    // Singleton pour gerer un seul manager pour toutes les instances du bot
    private final static MultiBotManager manager = new MultiBotManager();

    // Liste des chans à l'ecoute pour le cas ou il y aurait d'autres chans sur un ZNC qui genererait des reponses involontaires
    private final static List<String> listChannels = new ArrayList<>();

    // Map d'alias pour enregistrer le nom du nukenet sur la table site_from
    private final static List<String> serverAlias = new ArrayList<>();


    public static synchronized MultiBotManager getManager() {
        return manager;
    }

    public static synchronized List<String> getChannelsListManager() {
        return listChannels;
    }

    public static synchronized List<String> getServerAlias() {
        return serverAlias;
    }

}
