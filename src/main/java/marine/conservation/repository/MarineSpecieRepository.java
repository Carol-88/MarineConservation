package marine.conservation.repository;

import marine.conservation.model.ConservationProject;
import marine.conservation.model.MarineSpecie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarineSpecieRepository extends JpaRepository<MarineSpecie, Long> {
    List<MarineSpecie> findByProject(ConservationProject project);
}