package fr.infinity.irc.bot.dupecheck.pre;

import fr.infinity.irc.bot.dupecheck.release.Release;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitePreRepository  extends CrudRepository<SitePre, Long> {

    @Query("SELECT p FROM SitePre p WHERE p.release = :release")
    SitePre findByReleaseId(@Param("release") Release release);

    @Query("SELECT p FROM SitePre p WHERE p.release IN :release ORDER BY p.pre_at ASC")
    List<SitePre> findAllByListRelease(@Param("release") List<Release> release);

    @Query("SELECT p FROM SitePre p WHERE p.release.team_lowercase = :query ORDER BY p.pre_at ASC")
    List<SitePre> findAllByListTeamLowercase(@Param("query") String query);


}
