package marine.conservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marine.conservation.dto.habitat.HabitatRequestDTO;
import marine.conservation.dto.habitat.HabitatResponseDTO;
import marine.conservation.model.Habitat;
import marine.conservation.service.interfaces.HabitatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habitats")
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatService habitatService;

    // Create
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

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<HabitatResponseDTO> getById(@PathVariable Long id) {
        Habitat habitat = habitatService.findById(id);
        return habitat != null ? ResponseEntity.ok(toResponseDTO(habitat)) : ResponseEntity.notFound().build();
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<HabitatResponseDTO>> getAll() {
        List<HabitatResponseDTO> habitats = habitatService.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(habitats);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Mapping Helper ----------------
    private HabitatResponseDTO toResponseDTO(Habitat habitat) {
        return HabitatResponseDTO.builder()
                .id(habitat.getId())
                .name(habitat.getName())
                .description(habitat.getDescription())
                .location(habitat.getLocation())
                .build();
    }
}
