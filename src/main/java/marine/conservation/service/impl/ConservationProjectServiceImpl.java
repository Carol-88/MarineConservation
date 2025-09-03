package marine.conservation.service.impl;

import jakarta.transaction.Transactional;
import marine.conservation.dto.conservationProject.ConservationProjectRequestDTO;
import marine.conservation.dto.conservationProject.ConservationProjectResponseDTO;
import marine.conservation.dto.conservationProject.ConservationProjectUpdateDTO;
import marine.conservation.model.ConservationProject;
import marine.conservation.model.MarineSpecie;
import marine.conservation.repository.ConservationProjectRepository;
import marine.conservation.repository.MarineSpecieRepository;
import marine.conservation.service.interfaces.ConservationProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConservationProjectServiceImpl implements ConservationProjectService {
    @Autowired
    ConservationProjectRepository conservationProjectRepository;

    @Autowired
    private MarineSpecieRepository marineSpecieRepository;

    @Override
    public ConservationProjectResponseDTO createProject(ConservationProjectRequestDTO projectRequestDTO) {
        ConservationProject project = ConservationProject.builder()
                .name(projectRequestDTO.getName())
                .description(projectRequestDTO.getDescription())
                .initDate(LocalDate.parse(projectRequestDTO.getInitDate()))
                .finalDate(LocalDate.parse(projectRequestDTO.getFinalDate()))
                .build();
        ConservationProject savedProject = conservationProjectRepository.save(project);
        return mapToResponseDTO(savedProject);
    }

    @Override
    public List<ConservationProjectResponseDTO> getAllProjects() {
        return conservationProjectRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConservationProjectResponseDTO getProjectById(Long id) {
        ConservationProject project = conservationProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return mapToResponseDTO(project);
    }

    @Override
    public ConservationProjectResponseDTO updateProjectPut(Long id, ConservationProjectUpdateDTO projectPutDTO) {
        ConservationProject project = conservationProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        project.setName(projectPutDTO.getName());
        project.setDescription(projectPutDTO.getDescription());
        project.setInitDate(LocalDate.parse(projectPutDTO.getInitDate()));
        project.setFinalDate(LocalDate.parse(projectPutDTO.getFinalDate()));

        ConservationProject updatedProject = conservationProjectRepository.save(project);
        return mapToResponseDTO(updatedProject);
    }

    @Override
    public ConservationProjectResponseDTO updateProjectPatch(Long id, ConservationProjectUpdateDTO projectPatchDTO) {
        ConservationProject project = conservationProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        if (projectPatchDTO.getName() != null) {
            project.setName(projectPatchDTO.getName());
        }
        if (projectPatchDTO.getDescription() != null) {
            project.setDescription(projectPatchDTO.getDescription());
        }
        if (projectPatchDTO.getInitDate() != null) {
            project.setInitDate(LocalDate.parse(projectPatchDTO.getInitDate()));
        }
        if (projectPatchDTO.getFinalDate() != null) {
            project.setFinalDate(LocalDate.parse(projectPatchDTO.getFinalDate()));
        }

        ConservationProject updatedProject = conservationProjectRepository.save(project);
        return mapToResponseDTO(updatedProject);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        ConservationProject project = conservationProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        List<MarineSpecie> speciesList = marineSpecieRepository.findByProject(project);
        speciesList.forEach(species -> species.setProject(null));
        marineSpecieRepository.saveAll(speciesList);

        conservationProjectRepository.delete(project);
    }

    private ConservationProjectResponseDTO mapToResponseDTO(ConservationProject project) {
        return ConservationProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .initDate(String.valueOf(project.getInitDate()))
                .finalDate(String.valueOf(project.getFinalDate()))
                .build()
        ;
    }
}
