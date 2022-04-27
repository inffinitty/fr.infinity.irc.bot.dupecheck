package fr.infinity.irc.bot.dupecheck.quarantine;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Quarantine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "qua_id",unique=true, nullable = false)
    private Long id;

    @Column(name = "qua_commande")
    private String commande;

    @Column(name = "qua_error")
    private String commande_error;

    @Column(name = "qua_irc_commande")
    private String irc_commande;

    @Column(name = "qua_from_nickname")
    private String from_nickname;

    @Column(name = "qua_from_channel")
    private String from_channel;

    @Column(name = "qua_from_network")
    private String from_network;

    @Column(name = "qua_added_at")
    private Timestamp added_at;

    public Quarantine(String commande, String commande_error, String irc_commande, String from_nickname, String from_channel, String from_network, Timestamp added_at) {
        this.commande = commande;
        this.commande_error = commande_error;
        this.irc_commande = irc_commande;
        this.from_nickname = from_nickname;
        this.from_channel = from_channel;
        this.from_network = from_network;
        this.added_at = added_at;
    }

    public Quarantine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }

    public String getCommande_error() {
        return commande_error;
    }

    public void setCommande_error(String commande_error) {
        this.commande_error = commande_error;
    }

    public String getIrc_commande() {
        return irc_commande;
    }

    public void setIrc_commande(String irc_commande) {
        this.irc_commande = irc_commande;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname = from_nickname;
    }

    public String getFrom_channel() {
        return from_channel;
    }

    public void setFrom_channel(String from_channel) {
        this.from_channel = from_channel;
    }

    public String getFrom_network() {
        return from_network;
    }

    public void setFrom_network(String from_network) {
        this.from_network = from_network;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
