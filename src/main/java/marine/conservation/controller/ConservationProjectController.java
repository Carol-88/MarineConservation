package marine.conservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marine.conservation.dto.conservationProject.ConservationProjectRequestDTO;
import marine.conservation.dto.conservationProject.ConservationProjectResponseDTO;
import marine.conservation.dto.conservationProject.ConservationProjectUpdateDTO;
import marine.conservation.service.interfaces.ConservationProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 🔹 Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Conservation Projects", description = "Endpoints for managing conservation projects")
public class ConservationProjectController {

    private final ConservationProjectService projectService;

    // ------------------- Create -------------------
    @Operation(summary = "Create a new conservation project",
            description = "Adds a new conservation project to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ConservationProjectResponseDTO> createProject(
            @Valid @RequestBody ConservationProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.ok(projectService.createProject(projectRequestDTO));
    }

    // ------------------- Read All -------------------
    @Operation(summary = "Get all conservation projects",
            description = "Retrieves a list of all registered conservation projects.")
    @ApiResponse(responseCode = "200", description = "List of projects retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ConservationProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get a conservation project by ID",
            description = "Retrieves a specific conservation project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // ------------------- Update (PUT) -------------------
    @Operation(summary = "Update a conservation project (PUT)",
            description = "Updates all fields of a conservation project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> updatePutProject(
            @PathVariable Long id,
            @Valid @RequestBody ConservationProjectUpdateDTO projectUpdateDTO) {
        return ResponseEntity.ok(projectService.updateProjectPut(id, projectUpdateDTO));
    }

    // ------------------- Update (PATCH) -------------------
    @Operation(summary = "Partially update a conservation project (PATCH)",
            description = "Updates only the provided fields of a conservation project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ConservationProjectResponseDTO> updatePatchProject(
            @PathVariable Long id,
            @RequestBody ConservationProjectUpdateDTO projectUpdateDTO) {
        return ResponseEntity.ok(projectService.updateProjectPatch(id, projectUpdateDTO));
    }

    // ------------------- Delete -------------------
    @Operation(summary = "Delete a conservation project",
            description = "Removes a conservation project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
