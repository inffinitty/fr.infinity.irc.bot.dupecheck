package fr.infinity.irc.bot.dupecheck.from;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteFromService {

    private final SiteFromRepository siteFromRepository;

    @Autowired
    public SiteFromService(SiteFromRepository siteFromRepository) {
        this.siteFromRepository = siteFromRepository;
    }

    public SiteFrom getFom(GenericMessageEvent event, String serverAlias) {
        return saveSiteFrom(new SiteFrom(event.getUser().getNick(), ((MessageEvent) event).getChannel().getName(), serverAlias));
    }
    public SiteFrom getFromHiddenSite(GenericMessageEvent event, String serverAlias) {
        return saveSiteFrom(new SiteFrom(event.getUser().getNick(),
                ((MessageEvent) event).getChannel().getName(),
                serverAlias,
                "SITE",
                "#SITE",
                "SITE")
        );
    }
    public SiteFrom getFromHiddenChan(GenericMessageEvent event, String serverAlias) {
        return saveSiteFrom(new SiteFrom(event.getUser().getNick(),
                ((MessageEvent) event).getChannel().getName(),
                serverAlias,
                "HIDDEN",
                "#HIDDEN",
                "HIDDEN")

        );
    }

    private SiteFrom saveSiteFrom(SiteFrom siteFrom) {
        SiteFrom existedFrom = siteFromRepository.findSiteFrom(siteFrom.getNickname(), siteFrom.getChan(), siteFrom.getNetwork());
        if (existedFrom!= null) {
            return existedFrom;
        } else {
            return siteFromRepository.save(siteFrom);
        }
    }
}
