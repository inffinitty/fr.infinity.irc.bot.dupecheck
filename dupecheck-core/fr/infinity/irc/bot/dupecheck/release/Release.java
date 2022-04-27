package fr.infinity.irc.bot.dupecheck.release;

import fr.infinity.irc.bot.dupecheck.exactinfo.SiteExactInfo;
import fr.infinity.irc.bot.dupecheck.pre.SitePre;
import fr.infinity.irc.bot.dupecheck.releasenuke.ReleaseNuke;

import javax.persistence.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "release")
public class Release implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rlz_id",unique=true, nullable = false)
    private Long id;

    @Column(name = "rlz_name")
    private String name;

    @Column(name = "rlz_name_lowercase")
    private String name_lowercase;

    @Column(name = "rlz_team")
    private String team;

    @Column(name = "rlz_team_lowercase")
    private String team_lowercase;

    public Release() {
    }

    public Release(String name) {
        this.name = name;
        this.name_lowercase = name.toLowerCase();
        String patternStr = "-[a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()){
            String team = name.substring(matcher.start()+1);
            this.team = team;
            this.team_lowercase = team.toLowerCase();
        } else {
            this.team = "Unknown";
            this.team_lowercase = "unknown";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_lowercase() {
        return name_lowercase;
    }

    public String getTeam() {
        return team;
    }

    public String getTeam_lowercase() {
        return team_lowercase;
    }
}
