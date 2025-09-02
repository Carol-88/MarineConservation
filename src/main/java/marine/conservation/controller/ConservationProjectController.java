package marine.conservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marine.conservation.dto.conservationProject.ConservationProjectRequestDTO;
import marine.conservation.dto.conservationProject.ConservationProjectResponseDTO;
import marine.conservation.dto.conservationProject.ConservationProjectUpdateDTO;
import marine.conservation.service.interfaces.ConservationProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class
ConservationProjectController {

    private final ConservationProjectService projectService;

    @PostMapping
    public ResponseEntity<ConservationProjectResponseDTO> createProject(
            @Valid @RequestBody ConservationProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.ok(projectService.createProject(projectRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ConservationProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> updatePutProject(
            @PathVariable Long id,
            @Valid @RequestBody ConservationProjectUpdateDTO projectUpdateDTO) {
        return ResponseEntity.ok(projectService.updateProjectPut(id, projectUpdateDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> updatePatchProject(
            @PathVariable Long id,
            @RequestBody ConservationProjectUpdateDTO projectUpdateDTO) {
        return ResponseEntity.ok(projectService.updateProjectPatch(id, projectUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
