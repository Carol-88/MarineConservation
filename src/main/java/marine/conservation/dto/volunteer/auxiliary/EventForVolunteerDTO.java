package marine.conservation.dto.volunteer.auxiliary;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventForVolunteerDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocationDTO location;
    private int maxVolunteers;
}
