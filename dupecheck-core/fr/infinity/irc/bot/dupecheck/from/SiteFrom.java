package fr.infinity.irc.bot.dupecheck.from;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "site_from")
public class SiteFrom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "from_id",unique=true, nullable = false)
    private Long id;

    @Column(name = "from_nickname")
    private String nickname;

    @Column(name = "from_chan")
    private String chan;

    @Column(name = "from_network")
    private String network;

    @Column(name = "from_display_nickname")
    private String nickname_display;

    @Column(name = "from_display_chan")
    private String chan_display;

    @Column(name = "from_display_network")
    private String network_display;

    public SiteFrom(String nickname, String chan, String network, String nickname_display, String chan_display, String network_display) {
        this.nickname = nickname;
        this.chan = chan;
        this.network = network;
        this.nickname_display = nickname_display;
        this.chan_display = chan_display;
        this.network_display = network_display;
    }

    public SiteFrom(String nickname, String chan, String network) {
        this.nickname = nickname;
        this.chan = chan;
        this.network = network;
        this.nickname_display = nickname;
        this.chan_display = chan;
        this.network_display = network;

    }

    public SiteFrom() {
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getChan() {
        return chan;
    }

    public void setChan(String chan) {
        this.chan = chan;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNickname_display() {
        return nickname_display;
    }

    public void setNickname_display(String nickname_display) {
        this.nickname_display = nickname_display;
    }

    public String getChan_display() {
        return chan_display;
    }

    public void setChan_display(String chan_display) {
        this.chan_display = chan_display;
    }

    public String getNetwork_display() {
        return network_display;
    }

    public void setNetwork_display(String network_display) {
        this.network_display = network_display;
    }
}
