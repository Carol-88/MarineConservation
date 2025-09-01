package marine.conservation.service.interfaces;

import marine.conservation.model.Habitat;
import java.util.List;

/**
 * Service interface for managing Habitat entities.
 */
public interface HabitatService {

    /**
     * Save a new habitat.
     * @param habitat the habitat to save
     * @return the saved habitat
     */
    Habitat save(Habitat habitat);

    /**
     * Find a habitat by its ID.
     * @param id the ID of the habitat
     * @return the habitat if found, otherwise null
     */
    Habitat findById(Long id);

    /**
     * Retrieve all habitats.
     * @return list of habitats
     */
    List<Habitat> findAll();

    /**
     * Delete a habitat by its ID.
     * @param id the ID of the habitat to delete
     */
    void deleteById(Long id);

}
