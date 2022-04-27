package fr.infinity.irc.bot.dupecheck.file;

import fr.infinity.irc.bot.dupecheck.release.Release;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SiteFileRepository extends CrudRepository<SiteFile, Long> {

    @Query("SELECT f FROM SiteFile f WHERE f.release = :release")
    SiteFile getByRlzId(@Param("release") Release release);
}
