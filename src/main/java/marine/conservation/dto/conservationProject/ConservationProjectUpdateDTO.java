package marine.conservation.dto.conservationProject;

import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class ConservationProjectUpdateDTO {
    private String name;
    private String description;
    private String initDate;
    private String finalDate;
}
