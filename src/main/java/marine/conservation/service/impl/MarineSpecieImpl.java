package marine.conservation.service.impl;

import marine.conservation.dto.marineSpecie.MarineSpecieRequestDTO;
import marine.conservation.dto.marineSpecie.MarineSpecieResponseDTO;

import marine.conservation.model.ConservationProject;
import marine.conservation.model.MarineSpecie;
import marine.conservation.repository.MarineSpecieRepository;
import marine.conservation.service.interfaces.MarineSpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarineSpecieImpl implements MarineSpecieService {

    @Autowired
    private MarineSpecieRepository speciesRepository;

    @Autowired
    private ConservationProjectRepository projectRepository;

    @Override
    public MarineSpecieResponseDTO create(MarineSpecieRequestDTO dto) {
        MarineSpecie species = MarineSpecie.builder()
                .commonName(dto.getCommonName())
                .scientificName(dto.getScientificName())
                .conservationStatus(dto.getConservationStatus())
                .project(getProject(dto.getProjectId()))
                .build();

        return mapToResponseDTO(speciesRepository.save(species));
    }

    @Override
    public List<MarineSpecieResponseDTO> findAll() {
        return speciesRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MarineSpecieResponseDTO findById(Long id) {
        return speciesRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Marine species not found with ID: " + id));
    }


    @Override
    public void delete(Long id) {
        if (!speciesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Marine species not found with ID: " + id);
        }
        speciesRepository.deleteById(id);
    }

    // Helper: Convert Entity to Response DTO
    private MarineSpecieResponseDTO mapToResponseDTO(MarineSpecie species) {
        return MarineSpecieResponseDTO.builder()
                .id(species.getId())
                .commonName(species.getCommonName())
                .scientificName(species.getScientificName())
                .conservationStatus(species.getConservationStatus())
                .projectId(species.getProject() != null ? species.getProject().getId() : null)
                .projectName(species.getProject() != null ? species.getProject().getNombre() : null)
                .build();
    }

    // Helper: Validate + fetch project
    private ConservationProject getProject(Long projectId) {
        if (projectId == null) return null;
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
    }

    // Inline custom exception (if you don't have a separate file)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
