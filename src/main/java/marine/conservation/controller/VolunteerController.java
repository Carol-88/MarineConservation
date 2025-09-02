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

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerRepository volunteerRepository;

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        return volunteerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@Valid @RequestBody VolunteerRequestDTO dto) {
        Volunteer volunteer = Volunteer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .vNumber(dto.getVNumber())
                .dateCertification(dto.getCertificate().getDateCertification()) // Asumiendo que tu CertifiedVolunteer tiene esto
                .build();

        return ResponseEntity.ok(volunteerRepository.save(volunteer));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}