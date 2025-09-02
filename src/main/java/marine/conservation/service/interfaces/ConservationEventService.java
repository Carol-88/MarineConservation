package marine.conservation.service.interfaces;

import jakarta.validation.Valid;
import marine.conservation.dto.ConservationEvent.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventUpdateDTO;

import java.util.List;

public interface ConservationEventService {

    ConservationEventResponseDTO createEvent(ConservationEventRequestDTO requestDTO);

    ConservationEventResponseDTO getEventById(Long id);

    List<ConservationEventResponseDTO> getAllEvents();

    ConservationEventResponseDTO updateEventPut(Long id, ConservationEventUpdateDTO updateDTO);

    ConservationEventResponseDTO updateEventPatch(Long id, ConservationEventUpdateDTO updateDTO);

    void deleteEvent(Long id);
}
