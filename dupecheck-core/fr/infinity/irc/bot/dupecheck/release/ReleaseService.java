package fr.infinity.irc.bot.dupecheck.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseService {

    private final ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseService(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    public Release getRelease(Release release) {
        Release existedRelease = releaseRepository.findByName(release.getName());
        if (existedRelease!= null) {
            return existedRelease;
        } else {
            return releaseRepository.save(release);
        }
    }

}
