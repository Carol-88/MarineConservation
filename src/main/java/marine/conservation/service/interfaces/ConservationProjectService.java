package marine.conservation.service.interfaces;

import jakarta.validation.Valid;
import marine.conservation.dto.conservationProject.ConservationProjectRequestDTO;
import marine.conservation.dto.conservationProject.ConservationProjectResponseDTO;
import marine.conservation.dto.conservationProject.ConservationProjectUpdateDTO;

import java.util.List;

public interface ConservationProjectService {

    ConservationProjectResponseDTO createProject(@Valid ConservationProjectRequestDTO projectRequestDTO);
    List<ConservationProjectResponseDTO> getAllProjects();
    ConservationProjectResponseDTO getProjectById(Long id);
    ConservationProjectResponseDTO updateProjectPut(Long id, ConservationProjectUpdateDTO projectPutDTO);
    ConservationProjectResponseDTO updateProjectPatch(Long id, ConservationProjectUpdateDTO projectPatchDTO);
    void deleteProject(Long id);
}
