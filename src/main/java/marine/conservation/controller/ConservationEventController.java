package marine.conservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marine.conservation.dto.ConservationEvent.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventUpdateDTO;
import marine.conservation.service.interfaces.ConservationEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class ConservationEventController {

    private final ConservationEventService eventService;

    // ------------------- Create -------------------
    @PostMapping
    public ResponseEntity<ConservationEventResponseDTO> createEvent(
            @Valid @RequestBody ConservationEventRequestDTO requestDTO) {
        ConservationEventResponseDTO response = eventService.createEvent(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ------------------- Read All -------------------
    @GetMapping
    public ResponseEntity<List<ConservationEventResponseDTO>> getAllEvents() {
        List<ConservationEventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // ------------------- Read One -------------------
    @GetMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> getEventById(@PathVariable(name = "id") Long id) {
        ConservationEventResponseDTO response = eventService.getEventById(id);
        return ResponseEntity.ok(response);
    }

    // ------------------- Update -------------------
    @PutMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> updateEventPut(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ConservationEventUpdateDTO updateDTO) {
        ConservationEventResponseDTO response = eventService.updateEventPut(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    // ------------------- Patch Update -------------------
    @PatchMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> patchEventPatch(
            @PathVariable(name = "id") Long id,
            @RequestBody ConservationEventUpdateDTO updateDTO) {
        ConservationEventResponseDTO response = eventService.updateEventPatch(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    // ------------------- Delete -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(name = "id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}