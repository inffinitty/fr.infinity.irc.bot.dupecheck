package fr.infinity.irc.bot.dupecheck.releasefilter;

import javax.persistence.*;

@Entity
@Table(name = "release_filter")
public class ReleaseFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rf_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "rf_regex")
    private String filterRegex;

    public ReleaseFilter() {
    }

    public ReleaseFilter(Long id, String filterRegex) {
        this.id = id;
        this.filterRegex = filterRegex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilterRegex() {
        return filterRegex;
    }

    public void setFilterRegex(String filterRegex) {
        this.filterRegex = filterRegex;
    }

}
