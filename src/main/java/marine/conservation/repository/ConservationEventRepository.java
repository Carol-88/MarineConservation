package marine.conservation.repository;

import marine.conservation.model.ConservationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConservationEventRepository extends JpaRepository<ConservationEvent, Long> {
}
