package fr.infinity.irc.bot.dupecheck.pre;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.release.Release;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "site_pre")
public class SitePre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pre_id",unique=true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pre_rlz_id")
    private Release release;

    @ManyToOne
    @JoinColumn(name = "pre_from_id")
    private SiteFrom siteFrom;

    @Column(name = "pre_cat")
    private String cat;

    @Column(name = "pre_pre_at")
    private Timestamp pre_at;

    @Column(name = "pre_added_at")
    private Timestamp added_at;

    public SitePre(Release release, SiteFrom siteFrom, String cat, Timestamp pre_at, Timestamp added_at) {
        this.release = release;
        this.siteFrom = siteFrom;
        this.cat = cat.toUpperCase();
        this.pre_at = pre_at;
        this.added_at = added_at;
    }

    public SitePre() {
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

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat.toUpperCase();
    }

    public Timestamp getPre_at() {
        return pre_at;
    }

    public void setPre_at(Timestamp pre_at) {
        this.pre_at = pre_at;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
