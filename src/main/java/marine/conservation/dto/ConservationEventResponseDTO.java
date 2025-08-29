package marine.conservation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import marine.conservation.enums.Location;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class ConservationEventResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Location location;
    private int maxVolunteers;
}
