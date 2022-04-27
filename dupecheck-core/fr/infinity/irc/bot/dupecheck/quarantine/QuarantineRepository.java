package fr.infinity.irc.bot.dupecheck.quarantine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarantineRepository extends CrudRepository<Quarantine, Long> {
}
