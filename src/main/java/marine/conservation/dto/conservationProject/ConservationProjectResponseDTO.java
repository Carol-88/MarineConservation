package marine.conservation.dto.conservationProject;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import marine.conservation.model.ConservationEvent;

@Data
@Getter
@Builder
public class ConservationProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String initDate;
    private String finalDate;
    private ConservationEvent events;
}
