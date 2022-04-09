package fr.infinity.irc.bot.release;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pre")
public class Release {

    @Id
    @SequenceGenerator(name = "pre_id_seq",
            sequenceName = "pre_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_id_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "team")
    private String team;

    @Column(name = "cat")
    private String cat;

    @Column(name = "genre")
    private String genre;

    @Column(name = "url")
    private String url;

    @Column(name = "size")
    private Float size;

    @Column(name = "files")
    private Integer files;

    @Column(name = "source")
    private String source;

    @Column(name = "added_at")
    private Timestamp added_at;

    @Column(name = "pre_at")
    private Timestamp pre_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getFiles() {
        return files;
    }

    public void setFiles(Integer files) {
        this.files = files;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }

    public Timestamp getPre_at() {
        return pre_at;
    }

    public void setPre_at(Timestamp pre_at) {
        this.pre_at = pre_at;
    }

    public Release() {
    }

    public Release(String name, String team, String cat, String genre, String url, Float size, Integer files, String source, Timestamp added_at, Timestamp pre_at) {
        this.name = name;
        this.team = team;
        this.cat = cat;
        this.genre = genre;
        this.url = url;
        this.size = size;
        this.files = files;
        this.source = source;
        this.added_at = added_at;
        this.pre_at = pre_at;
    }


    public String displayInfo() {
        return cat + "/" + name + " / " + team + " / " genre + " / " + url + " / " + size + " / " + files + " / " + pre_at;
    }
}
