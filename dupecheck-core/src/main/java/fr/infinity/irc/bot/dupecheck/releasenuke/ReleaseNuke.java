package fr.infinity.irc.bot.dupecheck.releasenuke;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.release.Release;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "release_nuke")
public class ReleaseNuke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rn_id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rn_rlz_id")
    private Release release;

    @ManyToOne
    @JoinColumn(name = "rn_from_id")
    private SiteFrom siteFrom;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rn_etat")
    private ReleaseEtat releaseEtat;

    @Column(name = "rn_commentary")
    private String commentary;

    @Column(name = "rn_network")
    private String network;

    @Column(name = "rn_added_at")
    private Timestamp added_at;

    public ReleaseNuke() {
    }

    public ReleaseNuke(Release release, SiteFrom siteFrom, ReleaseEtat releaseEtat,
                       String commentary, String network, Timestamp added_at) {
        this.release = release;
        this.siteFrom = siteFrom;
        this.releaseEtat = releaseEtat;
        this.commentary = commentary;
        this.network = network;
        this.added_at = added_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public SiteFrom getSiteFrom() {
        return siteFrom;
    }

    public void setSiteFrom(SiteFrom siteFrom) {
        this.siteFrom = siteFrom;
    }

    public ReleaseEtat getReleaseEtat() {
        return releaseEtat;
    }

    public void setReleaseEtat(ReleaseEtat releaseEtat) {
        this.releaseEtat = releaseEtat;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
