package fr.infinity.irc.bot.dupecheck.exactinfo;

import fr.infinity.irc.bot.dupecheck.release.Release;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteExactInfoRepository extends CrudRepository<SiteExactInfo, Long> {

    @Query("SELECT e FROM SiteExactInfo e WHERE e.release = :release")
    SiteExactInfo findByReleaseId(@Param("release") Release release);
}
