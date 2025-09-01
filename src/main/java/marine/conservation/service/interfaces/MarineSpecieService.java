package marine.conservation.service.interfaces;

import marine.conservation.dto.marineSpecie.MarineSpecieRequestDTO;
import marine.conservation.dto.marineSpecie.MarineSpecieResponseDTO;

import java.util.List;

public interface MarineSpecieService {

    MarineSpecieResponseDTO create(MarineSpecieRequestDTO dto);

    List<MarineSpecieResponseDTO> findAll();

    MarineSpecieResponseDTO findById(Long id);

    void delete(Long id);
}