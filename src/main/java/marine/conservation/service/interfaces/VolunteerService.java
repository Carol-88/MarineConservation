package marine.conservation.service.interfaces;

import jakarta.validation.Valid;
import marine.conservation.dto.volunteer.VolunteerResponseDTO;
import marine.conservation.dto.volunteer.VolunteerRequestDTO;
import marine.conservation.dto.volunteer.VolunteerUpdateDTO;

import java.util.List;

public interface VolunteerService {
    VolunteerResponseDTO createVolunteer(@Valid VolunteerRequestDTO requestDTO);
    VolunteerResponseDTO getVolunteerById(Long id);
    List<VolunteerResponseDTO> getAllVolunteers();
    VolunteerResponseDTO updateVolunteerPut(Long id, VolunteerUpdateDTO updateDTO);
    VolunteerResponseDTO updateVolunteerPatch(Long id, VolunteerUpdateDTO updateDTO);
    void deleteVolunteer(Long id);
}
