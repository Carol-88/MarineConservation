package marine.conservation.service.interfaces;

import marine.conservation.dto.MarineSpeciesRequestDTO;
import marine.conservation.dto.MarineSpeciesResponseDTO;

import java.util.List;

public interface MarineSpeciesService {
    MarineSpeciesResponseDTO create(MarineSpeciesRequestDTO dto);
    List<MarineSpeciesResponseDTO> findAll();
    MarineSpeciesResponseDTO findById(Long id);
    MarineSpeciesResponseDTO update(Long id, MarineSpeciesRequestDTO dto);
    void delete(Long id);
}