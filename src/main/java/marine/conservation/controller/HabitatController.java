package marine.conservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marine.conservation.dto.habitat.HabitatRequestDTO;
import marine.conservation.dto.habitat.HabitatResponseDTO;
import marine.conservation.model.Habitat;
import marine.conservation.service.interfaces.HabitatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 🔹 Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habitats")
@RequiredArgsConstructor
@Tag(name = "Habitats", description = "Endpoints for managing marine habitats")
public class HabitatController {

    private final HabitatService habitatService;

    // ------------------- Create -------------------
    @Operation(summary = "Create a new habitat",
            description = "Adds a new habitat with its name, description, and location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<HabitatResponseDTO> create(@Valid @RequestBody HabitatRequestDTO dto) {
        Habitat saved = habitatService.save(
                Habitat.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .location(dto.getLocation())
                        .build()
        );
        return ResponseEntity.ok(toResponseDTO(saved));
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get habitat by ID",
            description = "Retrieves a habitat by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitat retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Habitat not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitatResponseDTO> getById(@PathVariable Long id) {
        Habitat habitat = habitatService.findById(id);
        return habitat != null ? ResponseEntity.ok(toResponseDTO(habitat)) : ResponseEntity.notFound().build();
    }

    // ------------------- Read All -------------------
    @Operation(summary = "Get all habitats",
            description = "Retrieves a list of all registered habitats.")
    @ApiResponse(responseCode = "200", description = "List of habitats retrieved successfully")
    @GetMapping
    public ResponseEntity<List<HabitatResponseDTO>> getAll() {
        List<HabitatResponseDTO> habitats = habitatService.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(habitats);
    }

    // ------------------- Update (PATCH) -------------------
    @Operation(summary = "Partially update a habitat (PATCH)",
            description = "Updates only the provided fields of a habitat.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Habitat not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<HabitatResponseDTO> updatePatch(
            @PathVariable Long id,
            @RequestBody HabitatRequestDTO dto) {

        Habitat habitat = habitatService.findById(id);
        if (habitat == null) {
            return ResponseEntity.notFound().build();
        }
        if (dto.getName() != null) {
            habitat.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            habitat.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            habitat.setLocation(dto.getLocation());
        }

        Habitat updated = habitatService.save(habitat);
        return ResponseEntity.ok(toResponseDTO(updated));
    }

    // ------------------- Delete -------------------
    @Operation(summary = "Delete a habitat",
            description = "Removes a habitat by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Habitat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Habitat not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------- Mapping Helper ----------------
    private HabitatResponseDTO toResponseDTO(Habitat habitat) {
        return HabitatResponseDTO.builder()
                .id(habitat.getId())
                .name(habitat.getName())
                .description(habitat.getDescription())
                .location(habitat.getLocation())
                .build();
    }
}
