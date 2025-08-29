package marine.conservation.repository;

import marine.conservation.model.MarineSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarineSpeciesRepository extends JpaRepository<MarineSpecies, Long> {
}