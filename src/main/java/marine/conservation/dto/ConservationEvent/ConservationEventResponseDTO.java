package marine.conservation.dto.ConservationEvent;

import lombok.*;
import marine.conservation.model.Location;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
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