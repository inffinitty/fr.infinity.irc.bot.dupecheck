package fr.infinity.irc.bot.dupecheck.exactinfo;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.release.Release;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "site_exactinfo")
public class SiteExactInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exactinfo_id",unique=true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exactinfo_rlz_id")
    private Release release;

    @ManyToOne
    @JoinColumn(name = "exactinfo_from_id")
    private SiteFrom siteFrom;

    @Column(name = "exactinfo_files")
    private int files;

    @Column(name = "exactinfo_size")
    private float size;

    @Column(name = "exactinfo_added_at")
    private Timestamp added_at;

    public SiteExactInfo() {
    }

    public SiteExactInfo(Release release, SiteFrom siteFrom, int files, float size, Timestamp added_at) {
        this.release = release;
        this.siteFrom = siteFrom;
        this.files = files;
        this.size = size;
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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
