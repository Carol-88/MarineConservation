package marine.conservation.controller;

import lombok.RequiredArgsConstructor;
import marine.conservation.dto.volunteer.VolunteerRequestDTO;
import marine.conservation.dto.volunteer.VolunteerUpdateDTO;
import marine.conservation.model.Volunteer;
import marine.conservation.repository.VolunteerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

// 🔹 Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
@Tag(name = "Volunteers", description = "Endpoints for managing volunteers")
public class VolunteerController {

    private final VolunteerRepository volunteerRepository;

    // ------------------- Read All -------------------
    @Operation(summary = "Get all volunteers",
            description = "Retrieves a list of all volunteers in the system.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get volunteer by ID",
            description = "Retrieves a volunteer by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        return volunteerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------- Create -------------------
    @Operation(summary = "Create a new volunteer",
            description = "Adds a new volunteer to the system with their certification date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@Valid @RequestBody VolunteerRequestDTO dto) {
        Volunteer volunteer = Volunteer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .vNumber(dto.getVNumber())
                .dateCertification(dto.getCertificate().getDateCertification())
                .build();

        return ResponseEntity.ok(volunteerRepository.save(volunteer));
    }

    // ------------------- Update (PUT) -------------------
    @Operation(summary = "Update volunteer (full)",
            description = "Updates all fields of an existing volunteer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteerPut(@PathVariable Long id,
                                                        @Valid @RequestBody VolunteerUpdateDTO dto) {
        return volunteerRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setPhone(dto.getPhone());
                    existing.setVNumber(dto.getVNumber());
                    existing.setDateCertification(dto.getCertificate().getDateCertification());
                    return ResponseEntity.ok(volunteerRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------- Update (PATCH) -------------------
    @Operation(summary = "Update volunteer (partial)",
            description = "Updates only the provided fields of an existing volunteer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteerPatch(@PathVariable Long id,
                                                          @RequestBody VolunteerUpdateDTO dto) {
        return volunteerRepository.findById(id)
                .map(existing -> {
                    if (dto.getName() != null) existing.setName(dto.getName());
                    if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
                    if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
                    if (dto.getVNumber() != null) existing.setVNumber(dto.getVNumber());
                    if (dto.getCertificate() != null) {
                        existing.setDateCertification(dto.getCertificate().getDateCertification());
                    }
                    return ResponseEntity.ok(volunteerRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------- Delete -------------------
    @Operation(summary = "Delete volunteer",
            description = "Removes a volunteer by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Volunteer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
