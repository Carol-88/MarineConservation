package marine.conservation.dto.ConservationEvent;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import marine.conservation.model.Location;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConservationEventUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 25)
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 100)
    private String description;

    @FutureOrPresent(message = "Start date must be present or in the future")
    private LocalDateTime startDateTime;

    @Future(message = "End date must be in the future")
    private LocalDateTime endDateTime;

    @NotNull(message = "Location cannot be null")
    @Valid
    private Location location;

    @Positive(message = "Maximum number of volunteers must be greater than 0")
    private Integer maxVolunteers;
}