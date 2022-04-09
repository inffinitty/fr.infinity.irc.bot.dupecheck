package fr.infinity.irc.bot.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ReleaseService {

    private final ReleaseRepository preRepository;

    @Autowired
    public ReleaseService(ReleaseRepository preRepository) {
        this.preRepository = preRepository;
    }

    public void saveSitePre(Release release) {
        List<Release> existReleaseList = preRepository.getLastRelease(release.getName(), PageRequest.of(0,1));
        if (existReleaseList.size() < 1) {
            preRepository.save(release);
        } else {

            Release existRelease = existReleaseList.get(0);

            if (existRelease.getCat() == null) {
                existRelease.setCat(release.getCat());
            }

            existRelease.setSource(release.getSource());
            preRepository.save(existRelease);
        }
    }

    public List<Release> dupe(String comQuery, int comParam) {
        return preRepository.getDupe(comQuery+"%", PageRequest.of(0,comParam));
    }

    public List<Release> activity(String team) {
        return preRepository.getActivity(team);
    }

    public long getDbStats() {
        return preRepository.getDBinfo();
    }

}
