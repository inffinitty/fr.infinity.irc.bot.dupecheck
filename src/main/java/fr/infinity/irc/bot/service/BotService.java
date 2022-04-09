package fr.infinity.irc.bot.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.MultiBotManager;
import org.pircbotx.UtilSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.infinity.irc.bot.service.Singleton.*;

@Service
public class BotService {

    private static final Log LOG = LogFactory.getLog(BotService.class);

    private static MultiBotManager manager;

    @Value("${fr.infinity.orc.bot.sessions:}")
    private String sessions;

    private final ListenerService listenerService;

    public BotService(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    public void execute() {

        LOG.info("Liste des sessions : " + sessions);

        if (this.sessions != null && !sessions.isBlank()) {
            JSONObject sessionsListJSONObject = new JSONObject(String.valueOf(sessions));
            JSONArray sessionsListJSONArray = sessionsListJSONObject.getJSONArray("sessions");

            List<Map> builderList = new ArrayList<>();

            sessionsListJSONArray.forEach(item -> {

                List<String> channelsList = getChannelsListManager();
                List<String> channelsInputList = getChannelsListInputManager();
                List<String> channelsOutputList = getChannelsListOutputManager();
                JSONObject itemObj = (JSONObject) item;

                JSONArray channelsListOutputJSONArray = itemObj.getJSONArray("channelsOutput");
                if (channelsListOutputJSONArray.length() >0 ) {
                    channelsListOutputJSONArray.forEach(channel-> {
                        JSONObject channelObj = (JSONObject) channel;
                        channelsList.add(channelObj.getString("channel"));
                        channelsOutputList.add(channelObj.getString("channel"));
                    });
                }

                JSONArray channelsListInputJSONArray = itemObj.getJSONArray("channelsInput");
                if (channelsListInputJSONArray.length() >0 ) {
                    channelsListInputJSONArray.forEach(channel-> {
                        JSONObject channelObj = (JSONObject) channel;
                        channelsList.add(channelObj.getString("channel"));
                        channelsInputList.add(channelObj.getString("channel"));
                    });
                }


                org.pircbotx.Configuration.Builder template = new org.pircbotx.Configuration.Builder()
                        .setName(itemObj.getString("nickname"))
                        .setLogin(itemObj.getString("login"))
                        .setRealName(itemObj.getString("realname"))
                        .setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates())
                        .addAutoJoinChannels(channelsList)
                        .addListener(listenerService)
                        .setAutoNickChange(true);

                Map<String, Object> templateBot = new HashMap<>();
                templateBot.put("template", template);
                templateBot.put("server",itemObj.getString("server"));
                templateBot.put("port",itemObj.getString("port"));
                templateBot.put("password",itemObj.getString("password"));
                builderList.add(templateBot);
            });

            manager = getManager();
            builderList.forEach(builder -> manager.addBot(((org.pircbotx.Configuration.Builder) builder.get("template"))
                    .buildForServer(builder.get("server").toString(), Integer.parseInt(builder.get("port").toString()), builder.get("password").toString()
                    )));
            manager.start();
        }
    }
}
