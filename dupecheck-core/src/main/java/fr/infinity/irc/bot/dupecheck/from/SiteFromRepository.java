package fr.infinity.irc.bot.dupecheck.from;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteFromRepository extends CrudRepository<SiteFrom, Long> {

    @Query("SELECT f FROM SiteFrom f WHERE f.nickname = :nickname AND f.chan = :chan AND f.network = :network")
    SiteFrom findSiteFrom(@Param("nickname") String nickname,
                          @Param("chan") String chan,
                          @Param("network") String network
                          );

}
