package marine.conservation.controller;

import lombok.RequiredArgsConstructor;
import marine.conservation.dto.volunteer.aux.EventForVolunteerDTO;
import marine.conservation.dto.volunteer.aux.LocationDTO;
import marine.conservation.dto.volunteer.aux.ProjectForVolunteerDTO;
import marine.conservation.dto.volunteer.VolunteerRequestDTO;
import marine.conservation.dto.volunteer.VolunteerResponseDTO;
import marine.conservation.dto.volunteer.VolunteerUpdateDTO;
import marine.conservation.model.Volunteer;
import marine.conservation.repository.VolunteerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<VolunteerResponseDTO> getAllVolunteers() {
        return volunteerRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get volunteer by ID",
            description = "Retrieves a volunteer by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VolunteerResponseDTO> getVolunteerById(@PathVariable Long id) {
        return volunteerRepository.findById(id)
                .map(volunteer -> ResponseEntity.ok(toResponseDTO(volunteer)))
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
    public ResponseEntity<VolunteerResponseDTO> createVolunteer(@Valid @RequestBody VolunteerRequestDTO dto) {
        Volunteer volunteer = Volunteer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .vNumber(dto.getVNumber())
                .dateCertification(dto.getCertificate().getDateCertification())
                .build();

        Volunteer saved = volunteerRepository.save(volunteer);
        return ResponseEntity.ok(toResponseDTO(saved));
    }

    // ------------------- Update (PUT) -------------------
    @Operation(summary = "Update volunteer (full)",
            description = "Updates all fields of an existing volunteer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VolunteerResponseDTO> updateVolunteerPut(@PathVariable Long id,
                                                                   @Valid @RequestBody VolunteerUpdateDTO dto) {
        return volunteerRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setPhone(dto.getPhone());
                    existing.setVNumber(dto.getVNumber());
                    existing.setDateCertification(dto.getCertificate().getDateCertification());
                    Volunteer saved = volunteerRepository.save(existing);
                    return ResponseEntity.ok(toResponseDTO(saved));
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
    public ResponseEntity<VolunteerResponseDTO> updateVolunteerPatch(@PathVariable Long id,
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
                    Volunteer saved = volunteerRepository.save(existing);
                    return ResponseEntity.ok(toResponseDTO(saved));
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

    // ------------------- Helper: Convert to VolunteerResponseDTO -------------------
    private VolunteerResponseDTO toResponseDTO(Volunteer volunteer) {
        return VolunteerResponseDTO.builder()
                .id(volunteer.getId())
                .name(volunteer.getName())
                .email(volunteer.getEmail())
                .phone(volunteer.getPhone())
                .vNumber(volunteer.getVNumber())
                .certificate(volunteer instanceof marine.conservation.model.CertifiedVolunteer ?
                        (marine.conservation.model.CertifiedVolunteer) volunteer : null)
                .projects(volunteer.getProjects().stream()
                        .map(p -> ProjectForVolunteerDTO.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .description(p.getDescription())
                                .initDate(p.getInitDate())
                                .finalDate(p.getFinalDate())
                                .events(p.getEvents().stream()
                                        .map(e -> EventForVolunteerDTO.builder()
                                                .id(e.getId())
                                                .name(e.getName())
                                                .description(e.getDescription())
                                                .startDateTime(e.getStartDateTime())
                                                .endDateTime(e.getEndDateTime())
                                                .location(LocationDTO.builder()
                                                        .type(e.getLocation().getType())
                                                        .region(e.getLocation().getRegion())
                                                        .latitude(e.getLocation().getLatitude())
                                                        .longitude(e.getLocation().getLongitude())
                                                        .build())
                                                .maxVolunteers(e.getMaxVolunteers())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
