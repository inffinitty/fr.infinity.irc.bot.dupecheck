package fr.infinity.irc.bot.dupecheck.releasefilter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseFilterRepository extends CrudRepository<ReleaseFilter, Long> {

    List<ReleaseFilter> findAll();
}
