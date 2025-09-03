package marine.conservation.dto.volunteer.aux;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProjectForVolunteerDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate initDate;
    private LocalDate finalDate;
    private List<EventForVolunteerDTO> events;
}
