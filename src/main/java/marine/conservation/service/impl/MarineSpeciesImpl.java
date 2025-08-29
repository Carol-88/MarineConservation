package marine.conservation.service.impl;

import marine.conservation.dto.MarineSpeciesRequestDTO;
import marine.conservation.dto.MarineSpeciesResponseDTO;
import marine.conservation.model.ConservationProject.*;

import marine.conservation.model.MarineSpecies;
import marine.conservation.repository.MarineSpeciesRepository;
import marine.conservation.service.interfaces.MarineSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarineSpeciesImpl implements MarineSpeciesService {

    @Autowired
    private MarineSpeciesRepository speciesRepository;

    @Autowired
    private ConservationProjectRepository projectRepository;

    @Override
    public MarineSpeciesResponseDTO create(MarineSpeciesRequestDTO dto) {
        MarineSpecies species = MarineSpecies.builder()
                .commonName(dto.getCommonName())
                .scientificName(dto.getScientificName())
                .conservationStatus(dto.getConservationStatus())
                .project(getProject(dto.getProjectId()))
                .build();

        return toResponseDTO(speciesRepository.save(species));
    }

    @Override
    public List<MarineSpeciesResponseDTO> findAll() {
        return speciesRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MarineSpeciesResponseDTO findById(Long id) {
        return speciesRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Marine species not found with ID: " + id));
    }


    @Override
    public MarineSpeciesResponseDTO update(Long id, MarineSpeciesRequestDTO dto) {
        MarineSpecies species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marine species not found with ID: " + id));

        species.setCommonName(dto.getCommonName());
        species.setScientificName(dto.getScientificName());
        species.setConservationStatus(dto.getConservationStatus());
        species.setProject(getProject(dto.getProjectId()));

        return toResponseDTO(speciesRepository.save(species));
    }

    @Override
    public void delete(Long id) {
        if (!speciesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Marine species not found with ID: " + id);
        }
        speciesRepository.deleteById(id);
    }

    // Helper: Convert Entity to Response DTO
    private MarineSpeciesResponseDTO toResponseDTO(MarineSpecies species) {
        return MarineSpeciesResponseDTO.builder()
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
