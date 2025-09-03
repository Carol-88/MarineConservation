package marine.conservation.controller;

import jakarta.validation.Valid;
import marine.conservation.dto.marineSpecie.MarineSpecieRequestDTO;
import marine.conservation.dto.marineSpecie.MarineSpecieResponseDTO;
import marine.conservation.service.interfaces.MarineSpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 🔹 Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/species")
@Tag(name = "Marine Species", description = "Endpoints for managing marine species")
public class MarineSpecieController {

    @Autowired
    private MarineSpecieService specieService;

    // ------------------- Create -------------------
    @Operation(summary = "Create a new marine species",
            description = "Adds a new marine species with its details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Species created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public MarineSpecieResponseDTO create(@Valid @RequestBody MarineSpecieRequestDTO dto) {
        return specieService.create(dto);
    }

    // ------------------- Read All -------------------
    @Operation(summary = "Get all marine species",
            description = "Retrieves a list of all registered marine species.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public List<MarineSpecieResponseDTO> getAll() {
        return specieService.findAll();
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get species by ID",
            description = "Retrieves a marine species by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Species retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Species not found")
    })
    @GetMapping("/{id}")
    public MarineSpecieResponseDTO getById(@PathVariable Long id) {
        return specieService.findById(id);
    }

    // ------------------- Delete -------------------
    @Operation(summary = "Delete a species",
            description = "Deletes a marine species by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Species deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Species not found")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        specieService.delete(id);
    }
}
