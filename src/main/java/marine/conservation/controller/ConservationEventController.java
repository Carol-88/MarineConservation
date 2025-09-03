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

// 🔹 Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Conservation Events", description = "Endpoints for managing conservation events")
public class ConservationEventController {

    private final ConservationEventService eventService;

    // ------------------- Create -------------------
    @Operation(summary = "Create a new conservation event",
            description = "Adds a new conservation event to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ConservationEventResponseDTO> createEvent(
            @RequestBody ConservationEventRequestDTO requestDTO) {
        ConservationEventResponseDTO response = eventService.createEvent(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ------------------- Read All -------------------
    @Operation(summary = "Get all conservation events",
            description = "Retrieves a list of all registered conservation events.")
    @ApiResponse(responseCode = "200", description = "List of events retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ConservationEventResponseDTO>> getAllEvents() {
        List<ConservationEventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // ------------------- Read One -------------------
    @Operation(summary = "Get a conservation event by ID",
            description = "Retrieves a specific conservation event by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> getEventById(
            @PathVariable(name = "id") Long id) {
        ConservationEventResponseDTO response = eventService.getEventById(id);
        return ResponseEntity.ok(response);
    }

    // ------------------- Update -------------------
    @Operation(summary = "Update a conservation event (PUT)",
            description = "Updates all fields of a conservation event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> updateEventPut(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ConservationEventUpdateDTO updateDTO) {
        ConservationEventResponseDTO response = eventService.updateEventPut(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    // ------------------- Patch Update -------------------
    @Operation(summary = "Partially update a conservation event (PATCH)",
            description = "Updates only the provided fields of a conservation event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ConservationEventResponseDTO> patchEventPatch(
            @PathVariable(name = "id") Long id,
            @RequestBody ConservationEventUpdateDTO updateDTO) {
        ConservationEventResponseDTO response = eventService.updateEventPatch(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    // ------------------- Delete -------------------
    @Operation(summary = "Delete a conservation event",
            description = "Removes a conservation event by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable(name = "id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
