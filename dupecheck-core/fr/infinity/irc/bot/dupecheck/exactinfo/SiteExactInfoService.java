package fr.infinity.irc.bot.dupecheck.exactinfo;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.from.SiteFromService;
import fr.infinity.irc.bot.dupecheck.release.Release;
import fr.infinity.irc.bot.dupecheck.release.ReleaseService;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SiteExactInfoService {

    private final SiteExactInfoRepository siteExactInfoRepository;
    private final ReleaseService releaseService;
    private final SiteFromService siteFromService;

    @Autowired
    public SiteExactInfoService(SiteExactInfoRepository siteExactInfoRepository,
                                ReleaseService releaseService,
                                SiteFromService siteFromService) {
        this.siteExactInfoRepository = siteExactInfoRepository;
        this.releaseService = releaseService;
        this.siteFromService = siteFromService;
    }

    public SiteExactInfo saveExactinfo(GenericMessageEvent event, String releaseName, Integer files, Float size, String serverAlias) {
        if (files != null && size != null && files > 0 && size > 0) {
            Release release = this.releaseService.getRelease(new Release(releaseName));
            SiteFrom siteFrom = siteFromService.getFom(event, serverAlias);
            Timestamp timestampNow = new Timestamp(event.getTimestamp());

            return this.save(release, siteFrom, files, size, timestampNow);
        }
        return null;
    }

    public SiteExactInfo save(Release release, SiteFrom siteFrom, int files, float size, Timestamp timestampNow) {
        SiteExactInfo newSiteExactInfo = new SiteExactInfo(release, siteFrom, files, size, timestampNow);
        SiteExactInfo existedExactInfo = this.siteExactInfoRepository.findByReleaseId(release);
        if (existedExactInfo != null) {
            return existedExactInfo;
        } else {
            return siteExactInfoRepository.save(newSiteExactInfo);
        }
    }
}
