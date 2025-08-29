package marine.conservation.dto.ConservationEvent;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import marine.conservation.enums.Location;

import java.time.LocalDateTime;

@Data
@Getter
public class ConservationEventUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @FutureOrPresent(message = "Start date must be present or in the future")
    private LocalDateTime startDateTime;

    @Future(message = "End date must be in the future")
    private LocalDateTime endDateTime;

    private Location location;

    @Positive(message = "Maximum number of volunteers must be greater than 0")
    private Integer maxVolunteers;
}
