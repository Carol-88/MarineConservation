package marine.conservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import marine.conservation.dto.ConservationEvent.ConservationEventRequestDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventResponseDTO;
import marine.conservation.dto.ConservationEvent.ConservationEventUpdateDTO;
import marine.conservation.model.Location;
import marine.conservation.service.interfaces.ConservationEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConservationEventController.class)
public class ConservationEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConservationEventService eventService;

    private ConservationEventResponseDTO eventResponseDTO;

    @BeforeEach
    void setup() {
        Location location = Location.builder()
                .latitude(0.0)
                .longitude(0.0)
                .build();

        eventResponseDTO = ConservationEventResponseDTO.builder()
                .id(1L)
                .name("Beach Cleanup")
                .description("Cleaning the local beach")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusHours(3))
                .location(location)
                .maxVolunteers(50)
                .build();
    }

    @Test
    void testCreateEvent() throws Exception {
        Location location = Location.builder().latitude(0.0).longitude(0.0).build();

        ConservationEventRequestDTO requestDTO = ConservationEventRequestDTO.builder()
                .name("Beach Cleanup")
                .description("Cleaning the local beach")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusHours(3))
                .location(location)
                .maxVolunteers(50)
                .build();

        Mockito.when(eventService.createEvent(any(ConservationEventRequestDTO.class)))
                .thenReturn(eventResponseDTO);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(eventResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(eventResponseDTO.getName()));
    }

    @Test
    void testGetAllEvents() throws Exception {
        Mockito.when(eventService.getAllEvents())
                .thenReturn(Arrays.asList(eventResponseDTO));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(eventResponseDTO.getId()));
    }

    @Test
    void testGetEventById() throws Exception {
        Mockito.when(eventService.getEventById(anyLong()))
                .thenReturn(eventResponseDTO);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventResponseDTO.getId()));
    }

    @Test
    void testUpdateEventPut() throws Exception {
        Location location = Location.builder().latitude(0.0).longitude(0.0).build();

        ConservationEventUpdateDTO updateDTO = ConservationEventUpdateDTO.builder()
                .name("Beach Cleanup Updated")
                .description("Updated description")
                .startDateTime(LocalDateTime.now().plusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(1).plusHours(4))
                .location(location)
                .maxVolunteers(60)
                .build();

        ConservationEventResponseDTO updatedResponse = ConservationEventResponseDTO.builder()
                .id(eventResponseDTO.getId())
                .name(updateDTO.getName())
                .description(updateDTO.getDescription())
                .startDateTime(updateDTO.getStartDateTime())
                .endDateTime(updateDTO.getEndDateTime())
                .location(updateDTO.getLocation())
                .maxVolunteers(updateDTO.getMaxVolunteers())
                .build();

        Mockito.when(eventService.updateEventPut(anyLong(), any(ConservationEventUpdateDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Beach Cleanup Updated"))
                .andExpect(jsonPath("$.maxVolunteers").value(60));
    }

    @Test
    void testPatchEventPatch() throws Exception {
        Location location = Location.builder().latitude(0.0).longitude(0.0).build();

        ConservationEventUpdateDTO updateDTO = ConservationEventUpdateDTO.builder()
                .location(location)
                .maxVolunteers(70)
                .build();

        ConservationEventResponseDTO updatedResponse = ConservationEventResponseDTO.builder()
                .id(eventResponseDTO.getId())
                .name(eventResponseDTO.getName())
                .description(eventResponseDTO.getDescription())
                .startDateTime(eventResponseDTO.getStartDateTime())
                .endDateTime(eventResponseDTO.getEndDateTime())
                .location(location)
                .maxVolunteers(70)
                .build();

        Mockito.when(eventService.updateEventPatch(anyLong(), any(ConservationEventUpdateDTO.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(patch("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxVolunteers").value(70));
    }

    @Test
    void testDeleteEvent() throws Exception {
        Mockito.doNothing().when(eventService).deleteEvent(anyLong());

        mockMvc.perform(delete("/api/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(eventService, Mockito.times(1)).deleteEvent(1L);
    }
}