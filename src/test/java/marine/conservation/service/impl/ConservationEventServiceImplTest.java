package marine.conservation.service.impl;

import marine.conservation.dto.ConservationEvent.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventUpdateDTO;
import marine.conservation.model.ConservationEvent;
import marine.conservation.model.Location;
import marine.conservation.repository.ConservationEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConservationEventServiceImplTest {

    @Mock
    private ConservationEventRepository eventRepository;

    @InjectMocks
    private ConservationEventServiceImpl service;

    private Location sampleLocation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear Location de ejemplo
        sampleLocation = Location.builder()
                .type("BEACH")
                .region("Atlantic Coast")
                .latitude(25.774)
                .longitude(-80.19)
                .build();
    }

    @Test
    void testCreateEvent() {
        ConservationEventRequestDTO requestDTO = ConservationEventRequestDTO.builder()
                .name("Beach Cleanup")
                .description("Cleaning the beach")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(2))
                .location(sampleLocation)
                .maxVolunteers(50)
                .build();

        ConservationEvent savedEvent = ConservationEvent.builder()
                .id(1L)
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .startDateTime(requestDTO.getStartDateTime())
                .endDateTime(requestDTO.getEndDateTime())
                .location(requestDTO.getLocation())
                .maxVolunteers(requestDTO.getMaxVolunteers())
                .build();

        when(eventRepository.save(any(ConservationEvent.class))).thenReturn(savedEvent);

        ConservationEventResponseDTO responseDTO = service.createEvent(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Beach Cleanup", responseDTO.getName());
        assertEquals("BEACH", responseDTO.getLocation().getType());
        assertEquals("Atlantic Coast", responseDTO.getLocation().getRegion());
        assertEquals(25.774, responseDTO.getLocation().getLatitude());
        assertEquals(-80.19, responseDTO.getLocation().getLongitude());
        verify(eventRepository, times(1)).save(any(ConservationEvent.class));
    }

    @Test
    void testGetEventById_Found() {
        ConservationEvent event = ConservationEvent.builder()
                .id(1L)
                .name("Event")
                .location(sampleLocation)
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        ConservationEventResponseDTO responseDTO = service.getEventById(1L);

        assertEquals("Event", responseDTO.getName());
        assertEquals("BEACH", responseDTO.getLocation().getType());
        assertEquals("Atlantic Coast", responseDTO.getLocation().getRegion());
    }

    @Test
    void testGetEventById_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getEventById(1L));
        assertEquals("Event not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetAllEvents() {
        ConservationEvent event1 = ConservationEvent.builder().id(1L).name("Event1").location(sampleLocation).build();
        ConservationEvent event2 = ConservationEvent.builder().id(2L).name("Event2").location(sampleLocation).build();

        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        List<ConservationEventResponseDTO> events = service.getAllEvents();

        assertEquals(2, events.size());
        assertEquals("BEACH", events.get(0).getLocation().getType());
        assertEquals("Atlantic Coast", events.get(1).getLocation().getRegion());
    }

    @Test
    void testUpdateEventPut() {
        // Evento existente
        ConservationEvent existingEvent = ConservationEvent.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(2))
                .location(sampleLocation)
                .maxVolunteers(50)
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Nueva location
        Location newLocation = Location.builder()
                .type("HARBOR")
                .region("Pacific Coast")
                .latitude(36.97)
                .longitude(-122.03)
                .build();

        // DTO con todos los campos completos (ninguno nulo)
        ConservationEventUpdateDTO updateDTO = ConservationEventUpdateDTO.builder()
                .name("New Name")
                .description("New Description")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusHours(3))
                .location(newLocation)
                .maxVolunteers(100)
                .build();

        ConservationEventResponseDTO updated = service.updateEventPut(1L, updateDTO);

        assertEquals("New Name", updated.getName());
        assertEquals("New Description", updated.getDescription());
        assertEquals(newLocation.getType(), updated.getLocation().getType());
        assertEquals(newLocation.getRegion(), updated.getLocation().getRegion());
        assertEquals(newLocation.getLatitude(), updated.getLocation().getLatitude());
        assertEquals(newLocation.getLongitude(), updated.getLocation().getLongitude());
        assertEquals(100, updated.getMaxVolunteers());
    }

    @Test
    void testUpdateEventPatch() {
        // Evento existente
        ConservationEvent existingEvent = ConservationEvent.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(2))
                .location(sampleLocation)
                .maxVolunteers(50)
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // DTO con solo algunos campos a actualizar
        ConservationEventUpdateDTO updateDTO = ConservationEventUpdateDTO.builder()
                .name("Updated Name")  // Cambiamos solo el nombre
                // maxVolunteers se deja null intencionadamente
                .build();

        ConservationEventResponseDTO updated = service.updateEventPatch(1L, updateDTO);

        assertEquals("Updated Name", updated.getName());
        assertEquals("Old Description", updated.getDescription());  // Mantiene los antiguos
        assertEquals("BEACH", updated.getLocation().getType());      // Location no cambia
        assertEquals("Atlantic Coast", updated.getLocation().getRegion());
        assertEquals(50, updated.getMaxVolunteers());               // Mantiene valor original
    }


    @Test
    void testDeleteEvent() {
        ConservationEvent existingEvent = ConservationEvent.builder()
                .id(1L)
                .name("Event")
                .location(sampleLocation)
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));

        service.deleteEvent(1L);

        verify(eventRepository, times(1)).delete(existingEvent);
    }
}
