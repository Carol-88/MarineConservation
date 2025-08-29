package marine.conservation.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import marine.conservation.enums.Location;

import java.time.LocalDateTime;

@Data
@Getter
public class ConservationEventRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Start date and time is required")
    @FutureOrPresent(message = "Start date must be present or in the future")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDateTime;

    @NotNull(message = "Location is required")
    private Location location;

    @Positive(message = "Maximum number of volunteers must be greater than 0")
    private int maxVolunteers;
}
