package marine.conservation.service.interfaces;

import marine.conservation.dto.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEventUpdateDTO;

import java.util.List;

public interface ConservationEventService {

    ConservationEventResponseDTO createEvent(ConservationEventRequestDTO requestDTO);

    ConservationEventResponseDTO getEventById(Long id);

    List<ConservationEventResponseDTO> getAllEvents();

    ConservationEventResponseDTO updateEvent(Long id, ConservationEventUpdateDTO updateDTO);

    void deleteEvent(Long id);
}
