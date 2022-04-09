package fr.infinity.irc.bot.release;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends CrudRepository<Release, Long> {

    @Query("SELECT r FROM Release r WHERE r.name LIKE :commandQuery ORDER BY r.pre_at DESC")
    List<Release> getDupe(@Param("commandQuery") String commandQuery, Pageable pageable);

    @Query("SELECT r FROM Release r WHERE r.team = :team ORDER BY r.pre_at ASC")
    List<Release> getActivity(@Param("team") String team);

    @Query("SELECT r FROM Release r WHERE r.name = :release ORDER BY r.pre_at DESC")
    List<Release> getLastRelease(@Param("release") String release, Pageable pageable);

}
