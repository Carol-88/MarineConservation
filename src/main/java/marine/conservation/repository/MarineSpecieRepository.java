package marine.conservation.repository;

import marine.conservation.model.MarineSpecie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarineSpecieRepository extends JpaRepository<MarineSpecie, Long> {
}