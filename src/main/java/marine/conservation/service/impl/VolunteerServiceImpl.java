package marine.conservation.service.impl;

import jakarta.transaction.Transactional;
import marine.conservation.dto.volunteer.VolunteerRequestDTO;
import marine.conservation.dto.volunteer.VolunteerResponseDTO;
import marine.conservation.dto.volunteer.VolunteerUpdateDTO;
import marine.conservation.model.ConservationEvent;
import marine.conservation.model.ConservationProject;
import marine.conservation.model.Volunteer;
import marine.conservation.repository.ConservationEventRepository;
import marine.conservation.repository.ConservationProjectRepository;
import marine.conservation.repository.VolunteerRepository;
import marine.conservation.service.interfaces.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ConservationProjectRepository projectRepository;

    @Autowired
    private ConservationEventRepository eventRepository;

    @Override
    public VolunteerResponseDTO createVolunteer(VolunteerRequestDTO requestDTO) {
        Volunteer volunteer = Volunteer.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .vNumber(requestDTO.getVNumber())
                .build();

        Volunteer saved = volunteerRepository.save(volunteer);
        return mapToResponseDTO(saved);
    }

    @Override
    public VolunteerResponseDTO getVolunteerById(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer not found with id: " + id));
        return mapToResponseDTO(volunteer);
    }

    @Override
    public List<VolunteerResponseDTO> getAllVolunteers() {
        return volunteerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VolunteerResponseDTO updateVolunteerPut(Long id, VolunteerUpdateDTO updateDTO) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer not found with id: " + id));

        volunteer.setName(updateDTO.getName());
        volunteer.setEmail(updateDTO.getEmail());
        volunteer.setPhone(updateDTO.getPhone());
        volunteer.setVNumber(updateDTO.getVNumber());

        Volunteer updated = volunteerRepository.save(volunteer);
        return mapToResponseDTO(updated);
    }

    @Override
    public VolunteerResponseDTO updateVolunteerPatch(Long id, VolunteerUpdateDTO updateDTO) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer not found with id: " + id));

        if (updateDTO.getName() != null) {
            volunteer.setName(updateDTO.getName());
        }
        if (updateDTO.getEmail() != null) {
            volunteer.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPhone() != null) {
            volunteer.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getVNumber() != null) {
            volunteer.setVNumber(updateDTO.getVNumber());
        }

        Volunteer updated = volunteerRepository.save(volunteer);
        return mapToResponseDTO(updated);
    }

    @Transactional
    @Override
    public void deleteVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer not found with id: " + id));

        volunteerRepository.delete(volunteer);
    }

    // ---------------- Mapping Methods ----------------

    private VolunteerResponseDTO mapToResponseDTO(Volunteer volunteer) {
        return VolunteerResponseDTO.builder()
                .id(volunteer.getId())
                .name(volunteer.getName())
                .email(volunteer.getEmail())
                .phone(volunteer.getPhone())
                .vNumber(volunteer.getVNumber())
                .build();
    }
}
