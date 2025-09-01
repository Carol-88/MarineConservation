package marine.conservation.repository;

import marine.conservation.model.ConservationProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConservationProjectRepository extends JpaRepository<ConservationProject, Long> {
}
