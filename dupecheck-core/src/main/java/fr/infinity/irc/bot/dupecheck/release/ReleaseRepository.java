package fr.infinity.irc.bot.dupecheck.release;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends CrudRepository<Release, Long> {

    @Query("SELECT r FROM Release r WHERE r.name = :releaseName")
    Release findByName(@Param("releaseName") String releaseName);

}
