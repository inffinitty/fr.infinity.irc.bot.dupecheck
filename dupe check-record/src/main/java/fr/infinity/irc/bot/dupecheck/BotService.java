package fr.infinity.irc.bot.dupecheck;

import fr.infinity.irc.bot.dupecheck.listeners.ListenerService;
import fr.infinity.irc.bot.dupecheck.service.SingletonFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.UtilSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.infinity.irc.bot.dupecheck.service.SingletonFactory.getChannelsListManager;

@Service
public class BotService {

    private static final Log LOG = LogFactory.getLog(BotService.class);

    @Value("${fr.infinity.irc.bot.dupecheck.networks:}")
    private String sessions;

    private final ListenerService listenerService;

    @Autowired
    public BotService(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    public void execute() {

        LOG.info("Sessions : " + sessions);

        if (sessions != null && !sessions.isBlank()) {
            JSONObject sessionsListJSONObject = new JSONObject(String.valueOf(sessions));
            JSONArray sessionsListJSONArray = sessionsListJSONObject.getJSONArray("sessions");

            List<Map> builderList = new ArrayList<>();

            sessionsListJSONArray.forEach(item -> {

                List<String> serverChannelsList = new ArrayList<>();
                JSONObject itemObj = (JSONObject) item;

                SingletonFactory.getServerAlias().add(itemObj.getString("serverAlias"));

                try {
                    JSONArray channelsListInputJSONArray = itemObj.getJSONArray("channels");
                    channelsListInputJSONArray.forEach(channel-> {
                        JSONObject channelObj = (JSONObject) channel;
                        getChannelsListManager().add(channelObj.getString("channel"));
                        serverChannelsList.add(channelObj.getString("channel"));
                    });
                } catch (Exception e) {}

                org.pircbotx.Configuration.Builder template = new org.pircbotx.Configuration.Builder()
                        .setName(itemObj.getString("nickname"))
                        .setLogin(itemObj.getString("login"))
                        .setRealName(itemObj.getString("realname"))
                        .setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates())
                        .addAutoJoinChannels(serverChannelsList)
                        .addListener(listenerService)
                        .setMessageDelay(200)
                        .setOnJoinWhoEnabled(false)
                        .setAutoNickChange(true);

                Map<String, Object> templateBot = new HashMap<>();
                templateBot.put("template", template);
                templateBot.put("server",itemObj.getString("server"));
                templateBot.put("port",itemObj.getString("port"));
                templateBot.put("password",itemObj.getString("password"));
                builderList.add(templateBot);
            });

            builderList.forEach(builder -> SingletonFactory.getManager().addBot(((org.pircbotx.Configuration.Builder) builder.get("template"))
                    .buildForServer(builder.get("server").toString(), Integer.parseInt(builder.get("port").toString()), builder.get("password").toString()
                    )));
            SingletonFactory.getManager().start();
        }
    }
}
