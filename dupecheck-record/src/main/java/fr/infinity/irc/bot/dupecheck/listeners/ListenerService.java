package fr.infinity.irc.bot.dupecheck.listeners;

import fr.infinity.irc.bot.dupecheck.exactinfo.SiteExactInfoService;
import fr.infinity.irc.bot.dupecheck.file.SiteFileService;
import fr.infinity.irc.bot.dupecheck.pre.SitePreService;
import fr.infinity.irc.bot.dupecheck.quarantine.QuarantineService;
import fr.infinity.irc.bot.dupecheck.releasefilter.ReleaseFilterService;
import fr.infinity.irc.bot.dupecheck.releasenuke.ReleaseNukeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.infinity.irc.bot.dupecheck.service.SingletonFactory.getChannelsListManager;
import static fr.infinity.irc.bot.dupecheck.service.SingletonFactory.getServerAlias;

@Service
public class ListenerService extends ListenerAdapter {

    private static final Log LOG = LogFactory.getLog(ListenerAdapter.class);

    private final SitePreService sitePreService;
    private final SiteExactInfoService siteExactInfoService;
    private final SiteFileService siteFileService;
    private final ReleaseFilterService releaseFilterService;
    private final ReleaseNukeService releaseNukeService;
    private final QuarantineService quarantineService;

    @Autowired
    public ListenerService(SitePreService sitePreService,
                           SiteExactInfoService siteExactInfoService,
                           SiteFileService siteFileService,
                           ReleaseFilterService releaseFilterService,
                           ReleaseNukeService releaseNukeService,
                           QuarantineService quarantineService) {
        this.sitePreService = sitePreService;
        this.siteExactInfoService = siteExactInfoService;
        this.siteFileService = siteFileService;
        this.releaseFilterService = releaseFilterService;
        this.releaseNukeService = releaseNukeService;
        this.quarantineService = quarantineService;
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        String userCommand = event.getMessage();
        if (getChannelsListManager().contains(((MessageEvent) event).getChannel().getName()) && "!".equals(userCommand.substring(0,1))) {
            this.queueExecution(event);
        }
    }

    private synchronized void queueExecution(GenericMessageEvent event) {
        String serverAlias = getServerAlias().get(event.getBot().getBotId());
        String[] parsedString = event.getMessage().split(" ");

        String command = parsedString[0];

        if(validationRelease(serverAlias, event, parsedString)) {
            switch (command) {
                case "!sitepre" -> executeSitePre(serverAlias, event, parsedString);
                case "!exactinfo", "!siteinfo" -> executeSiteExactInfo(serverAlias, event, parsedString);
                case "!addold" -> executeSiteAddOld(serverAlias, event, parsedString);
                case "!addnfo" -> executeSiteFile(serverAlias, event, parsedString, "NFO");
                case "!addmediainfo" -> executeSiteFile(serverAlias, event, parsedString, "MEDIAINFO");
                case "!addxml" -> executeSiteFile(serverAlias, event, parsedString, "MEDIAINFOXML");
                case "!nuke" -> executeNuke(serverAlias, event, parsedString);
                case "!unnuke" -> executeUnnuke(serverAlias, event, parsedString);
                case "!modnuke" -> executeModnuke(serverAlias, event, parsedString);
                case "!delpre" -> executeDelPre(serverAlias, event, parsedString);
                default -> {
                }
            }
        }
    }

    private boolean validationRelease(String serverAlias, GenericMessageEvent event, String[] parsedString) {

        boolean result = releaseFilterService.checkRelease(parsedString[1]);

        if(!result){
            this.quarantineExecute(serverAlias, event, parsedString, "Release pattern not match");
        }

        return result;
    }

    public void executeSitePre(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            String releaseName = parsedString[1];
            String cat = parsedString[2].toUpperCase();

            Integer files = this.parseFiles(parsedString[3]);
            Float size = this.parseSize(parsedString[4]);

            this.sitePreService.savePre(event, releaseName, cat, serverAlias);
            this.siteExactInfoService.saveExactinfo(event, releaseName, files, size, serverAlias);
        } catch (Exception e) {
            this.quarantineExecute(serverAlias, event, parsedString, "Saving Error");
        }
    }

    public void executeSiteExactInfo(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            String releaseName = parsedString[1];

            Integer files = parseFiles(parsedString[3]);
            Float size = parseSize(parsedString[4]);

            this.siteExactInfoService.saveExactinfo(event, releaseName, files, size, serverAlias);
        } catch (DataIntegrityViolationException e) {
            LOG.error("Impossible enregistrer executeSiteExactInfo() " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Saving Error");
        }
    }

    private void executeSiteAddOld(String serverAlias, GenericMessageEvent event, String[] parsedString) {

        try {
            String releaseName = parsedString[1];
            String cat = parsedString[2].toUpperCase();
            long preUnixTmestamp = Long.parseLong(parsedString[3]);
            Timestamp preTimestamp = new Timestamp(preUnixTmestamp * 1000L);

            Integer files = parseFiles(parsedString[4]);
            Float size = parseSize(parsedString[5]);

            this.sitePreService.savePre(event, releaseName, cat, serverAlias, preTimestamp);
            this.siteExactInfoService.saveExactinfo(event, releaseName, files, size, serverAlias);

        } catch (DataIntegrityViolationException e) {
            this.quarantineExecute(serverAlias, event, parsedString, "Saving Error");
        }
    }


    private void executeSiteFile(String serverAlias, GenericMessageEvent event, String[] parsedString, String filetype) {

        try {
            String releaseName = parsedString[1];
            String uri = parsedString[2];
            String filename = parsedString[3];
            String crc32 = parsedString[4];

            if ("MEDIAINFO".equals(filetype)) {
                filename += ".txt";
            } else if ("MEDIAINFOXML".equals(filetype)) {
                filename += ".xml";
            }
            this.siteFileService.saveFile(event, releaseName, uri, filename, crc32, filetype, serverAlias);

        } catch (DataIntegrityViolationException e) {
            LOG.error("Impossible enregistrer executeSiteFile() " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Saving Error");
        } catch (MalformedURLException e) {
            LOG.error("Erreur sur l'url de " + parsedString[1] + "(" + parsedString[2] + ")");
            this.quarantineExecute(serverAlias, event, parsedString, "Url Error");
        } catch (IOException e) {
            LOG.error("Erreur lors de l'écriture du fichier " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "No Such File Found");
        }
    }


    private void executeNuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            this.releaseNukeService.nuke(serverAlias, event, parsedString);
        } catch (Exception e) {
            LOG.error("Erreur lors du nuke de la release " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Can't execute nuke");
        }
    }

    private void executeUnnuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            this.releaseNukeService.unnuke(serverAlias, event, parsedString);
        } catch (Exception e) {
            LOG.error("Erreur lors du unnuke de la release " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Can't execute unnuke");
        }
    }

    private void executeModnuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            this.releaseNukeService.modnuke(serverAlias, event, parsedString);
        } catch (Exception e) {
            LOG.error("Erreur lors du modnuke de la release " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Can't execute modnuke");
        }
    }

    private void executeDelPre(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        try {
            this.releaseNukeService.delpre(serverAlias, event, parsedString);
        } catch (Exception e) {
            LOG.error("Erreur lors de la delpre de la release " + parsedString[1]);
            this.quarantineExecute(serverAlias, event, parsedString, "Can't delpre release");
        }
    }

    private void quarantineExecute(String serverAlias, GenericMessageEvent event, String[] parsedString, String error_msg) {
        String command = parsedString[0];
        this.quarantineService.saveQuarantine(event, command, error_msg, serverAlias);
    }

    private Integer parseFiles(String filesString) {
        Pattern patternFiles = Pattern.compile("^[0-9]*$");
        Matcher matcherFiles = patternFiles.matcher(filesString);
        if(matcherFiles.find()){
            return Integer.valueOf(filesString);
        }
        return null;
    }

    private Float parseSize(String sizeString) {
        Pattern patternSize = Pattern.compile("^[0-9.]*$");
        Matcher matcherSize = patternSize.matcher(sizeString);
        if(matcherSize.find()){
            return Float.valueOf(sizeString);
        }
        return null;
    }

}
