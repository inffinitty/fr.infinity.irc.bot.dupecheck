package fr.infinity.irc.bot.dupecheck.releasenuke;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseNukeRepository extends CrudRepository<ReleaseNuke, Long> {
}
