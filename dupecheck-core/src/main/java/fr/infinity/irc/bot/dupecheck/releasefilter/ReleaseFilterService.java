package fr.infinity.irc.bot.dupecheck.releasefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReleaseFilterService {

    private ReleaseFilterRepository releaseFilterRepository;

    @Autowired
    public ReleaseFilterService(ReleaseFilterRepository releaseFilterRepository) {
        this.releaseFilterRepository = releaseFilterRepository;
    }

    private List<ReleaseFilter> getAllFilters() {
        return releaseFilterRepository.findAll();
    }

    public boolean checkRelease(String release) {

        release = release.toLowerCase();
        String releaseWithoutGrp = "";
        boolean result = true;
        List<ReleaseFilter> releaseFilters = getAllFilters();

        String patternStrTeam = "-[a-zA-Z0-9_]*$";
        Pattern patternRlz = Pattern.compile(patternStrTeam);
        Matcher matcherRlz = patternRlz.matcher(release);
        if(matcherRlz.find()){
            releaseWithoutGrp = release.substring(0, matcherRlz.start());
        } else {
            result = false;
        }

        // Controle que le nom de la release sans le groupe n'ait que les caracteres autorise : a-zA-Z_-()
        if (result) {
            Pattern patternReleaseGlobal = Pattern.compile("^[a-z0-9._\\-()]*$");
            Matcher matcherGlobal = patternReleaseGlobal.matcher(releaseWithoutGrp);
            if(matcherGlobal.find()){
                result = true;
            } else {
                result = false;
            }
        }

        // Controle que le nom de la release ne contienne pas de chaine de caracteres contenant les regex interdits
        if (result) {
            for(ReleaseFilter releaseFilter : releaseFilters) {
                Pattern patternRelease = Pattern.compile(releaseFilter.getFilterRegex());
                Matcher matcher = patternRelease.matcher(releaseWithoutGrp);
                if(matcher.find()){
                    result = false;
                }
            }
        }

        return result;
    }
}
