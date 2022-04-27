package fr.infinity.irc.bot.dupecheck.pre;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.from.SiteFromService;
import fr.infinity.irc.bot.dupecheck.release.Release;
import fr.infinity.irc.bot.dupecheck.release.ReleaseService;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Service
public class SitePreService {

    private final SitePreRepository sitePreRepository;
    private final ReleaseService releaseService;
    private final SiteFromService siteFromService;


    @Autowired
    public SitePreService(SitePreRepository sitePreRepository,
                          ReleaseService releaseService,
                          SiteFromService siteFromService) {
        this.sitePreRepository = sitePreRepository;
        this.releaseService = releaseService;
        this.siteFromService = siteFromService;
    }

    public SitePre savePre(GenericMessageEvent event, String releaseName, String cat, String serverAlias) {
        Release release = this.releaseService.getRelease(new Release(releaseName));
        SiteFrom siteFrom = siteFromService.getFom(event, serverAlias);
        Timestamp preTime = new Timestamp(event.getTimestamp());

        return this.save(release, siteFrom, cat, preTime, preTime,false);
    }

    public SitePre savePre(GenericMessageEvent event, String releaseName, String cat, String serverAlias, Timestamp preTime) {
        Release release = this.releaseService.getRelease(new Release(releaseName));
        SiteFrom siteFrom = siteFromService.getFom(event, serverAlias);
        Timestamp addTime = new Timestamp(event.getTimestamp());

        return this.save(release, siteFrom, cat, preTime, addTime, true);
    }

    public SitePre save(Release release, SiteFrom siteFrom, String cat, Timestamp preTime,Timestamp addTime, boolean forcedPretime) {
        SitePre newSitePre = new SitePre(release, siteFrom, cat, preTime, addTime);
        SitePre existedSitePre = this.sitePreRepository.findByReleaseId(release);

        if (existedSitePre != null) {
            boolean toUpdate = false;

            // si cat est inconnu (PRE)
            if("PRE".equals(existedSitePre.getCat()) && !"PRE".equals(newSitePre.getCat())) {
                existedSitePre.setCat(newSitePre.getCat());
                toUpdate = true;
            }
            // si le new timestamp est avant l'ancien
            if(existedSitePre.getPre_at().after(newSitePre.getPre_at())) {
                existedSitePre.setPre_at(newSitePre.getPre_at());
                toUpdate = true;
            }
            // si l'option forcedpretime est activé
            if (forcedPretime && existedSitePre.getPre_at() != newSitePre.getPre_at()) {
                existedSitePre.setPre_at(newSitePre.getPre_at());
                toUpdate = true;
            }
            // s'il y a eu un changement dans l'existedsitepre
            if (toUpdate) {
                existedSitePre = this.sitePreRepository.save(existedSitePre);
            }
            return existedSitePre;
        } else {
            return this.sitePreRepository.save(newSitePre);
        }
    }

    public List<SitePre> getAllSitePreByTeam(List<Release> releases) {

        return this.sitePreRepository.findAllByListRelease(releases);

    }

    public List<SitePre> getAllSitePreByTeam(String team) {

        return this.sitePreRepository.findAllByListTeamLowercase(team);

    }

}
