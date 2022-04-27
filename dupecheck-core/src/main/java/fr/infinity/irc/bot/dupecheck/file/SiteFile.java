package fr.infinity.irc.bot.dupecheck.file;

import fr.infinity.irc.bot.dupecheck.from.SiteFrom;
import fr.infinity.irc.bot.dupecheck.release.Release;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "site_file")
public class SiteFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "file_id",unique=true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "file_rlz_id")
    private Release release;

    @ManyToOne
    @JoinColumn(name = "file_from_id")
    private SiteFrom siteFrom;

    @Column(name="file_binary")
    private byte[] file;

    @Column(name="file_filename")
    private String filename;

    @Column(name="file_filetype")
    private String filetype;

    @Column(name="file_crc32")
    private String crc32;

    @Column(name = "file_added_at")
    private Timestamp added_at;

    public SiteFile() {
    }

    public SiteFile(Release release, SiteFrom siteFrom, byte[] file, String filename, String filetype, String crc32, Timestamp added_at) {
        this.release = release;
        this.siteFrom = siteFrom;
        this.file = file;
        this.filename = filename;
        this.filetype = filetype;
        this.crc32 = crc32;
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCrc32() {
        return crc32;
    }

    public void setCrc32(String crc32) {
        this.crc32 = crc32;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
