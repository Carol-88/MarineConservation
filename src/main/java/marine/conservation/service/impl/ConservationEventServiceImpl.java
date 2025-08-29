package marine.conservation.service.impl;

import lombok.RequiredArgsConstructor;
import marine.conservation.dto.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEventUpdateDTO;
import marine.conservation.model.ConservationEvent;
import marine.conservation.repository.ConservationEventRepository;
import marine.conservation.service.interfaces.ConservationEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConservationEventServiceImpl implements ConservationEventService {

    private final ConservationEventRepository eventRepository;

    @Override
    public ConservationEventResponseDTO createEvent(ConservationEventRequestDTO requestDTO) {
        ConservationEvent event = ConservationEvent.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .startDateTime(requestDTO.getStartDateTime())
                .endDateTime(requestDTO.getEndDateTime())
                .location(requestDTO.getLocation())
                .maxVolunteers(requestDTO.getMaxVolunteers())
                .build();

        ConservationEvent saved = eventRepository.save(event);
        return mapToResponseDTO(saved);
    }

    @Override
    public ConservationEventResponseDTO getEventById(Long id) {
        ConservationEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        return mapToResponseDTO(event);
    }

    @Override
    public List<ConservationEventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConservationEventResponseDTO updateEvent(Long id, ConservationEventUpdateDTO updateDTO) {
        ConservationEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        if (updateDTO.getName() != null) event.setName(updateDTO.getName());
        if (updateDTO.getDescription() != null) event.setDescription(updateDTO.getDescription());
        if (updateDTO.getStartDateTime() != null) event.setStartDateTime(updateDTO.getStartDateTime());
        if (updateDTO.getEndDateTime() != null) event.setEndDateTime(updateDTO.getEndDateTime());
        if (updateDTO.getLocation() != null) event.setLocation(updateDTO.getLocation());
        if (updateDTO.getMaxVolunteers() != null) event.setMaxVolunteers(updateDTO.getMaxVolunteers());

        ConservationEvent updated = eventRepository.save(event);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        ConservationEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        eventRepository.delete(event);
    }

    // ---------------- Mapping Methods ----------------

    private ConservationEventResponseDTO mapToResponseDTO(ConservationEvent event) {
        return ConservationEventResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .location(event.getLocation())
                .maxVolunteers(event.getMaxVolunteers())
                .build();
    }
}
