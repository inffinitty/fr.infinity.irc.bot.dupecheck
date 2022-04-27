package fr.infinity.irc.bot.dupecheck.releasenuke;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.from.SiteFromService;
import fr.infinity.irc.bot.dupecheck.release.Release;
import fr.infinity.irc.bot.dupecheck.release.ReleaseService;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReleaseNukeService {

    private final ReleaseNukeRepository releaseNukeRepository;
    private final ReleaseService releaseService;
    private final SiteFromService siteFromService;

    @Autowired
    public ReleaseNukeService(ReleaseNukeRepository releaseNukeRepository,
                              ReleaseService releaseService,
                              SiteFromService siteFromService) {
        this.releaseNukeRepository = releaseNukeRepository;
        this.releaseService = releaseService;
        this.siteFromService = siteFromService;
    }

    public void nuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        this.save(serverAlias, event, parsedString, ReleaseEtat.NUKE);
    }

    public void unnuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        this.save(serverAlias, event, parsedString, ReleaseEtat.UNNUKE);
    }

    public void modnuke(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        this.save(serverAlias, event, parsedString, ReleaseEtat.MODNUKE);
    }

    public void delpre(String serverAlias, GenericMessageEvent event, String[] parsedString) {
        this.save(serverAlias, event, parsedString, ReleaseEtat.DELPRE);
    }

    private void save(String serverAlias, GenericMessageEvent event, String[] parsedString, ReleaseEtat etat) {
        String releaseName = parsedString[1];
        String commentary = parsedString[2];
        String network = parsedString[3];

        Release release = this.releaseService.getRelease(new Release(releaseName));
        SiteFrom siteFrom = siteFromService.getFom(event, serverAlias);
        Timestamp addedNow = new Timestamp(event.getTimestamp());

        ReleaseNuke releaseToExec = new ReleaseNuke(release, siteFrom, etat, commentary, network, addedNow);

        this.releaseNukeRepository.save(releaseToExec);
    }

}
