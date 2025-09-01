package marine.conservation.repository;

import marine.conservation.model.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitatRepository extends JpaRepository<Habitat, Long> {}
