package fr.infinity.irc.bot.dupecheck.file;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.from.SiteFromService;
import fr.infinity.irc.bot.dupecheck.release.Release;
import fr.infinity.irc.bot.dupecheck.release.ReleaseService;
import org.apache.commons.io.IOUtils;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;

@Service
public class SiteFileService {
    private final SiteFileRepository siteFileRepository;
    private final ReleaseService releaseService;
    private final SiteFromService siteFromService;

    @Autowired
    public SiteFileService(SiteFileRepository siteFileRepository,
                           ReleaseService releaseService,
                           SiteFromService siteFromService) {
        this.siteFileRepository = siteFileRepository;
        this.releaseService = releaseService;
        this.siteFromService = siteFromService;
    }

    public SiteFile saveFile(GenericMessageEvent event, String releaseName, String uri, String filename, String crc32, String filetype, String serverAlias) throws IOException {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);

        Release release = this.releaseService.getRelease(new Release(releaseName));
        SiteFrom siteFrom = siteFromService.getFom(event, serverAlias);
        Timestamp timestampNow = new Timestamp(event.getTimestamp());

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(uri).openStream());

        SiteFile newSiteFile = new SiteFile(release, siteFrom, IOUtils.toByteArray(bufferedInputStream), filename, filetype, crc32, timestampNow);
        SiteFile existedSiteFile = this.siteFileRepository.getByRlzId(release);

        if (existedSiteFile != null) {
            return this.siteFileRepository.save(existedSiteFile);
        } else {
            return this.siteFileRepository.save(newSiteFile);
        }
    }
}
