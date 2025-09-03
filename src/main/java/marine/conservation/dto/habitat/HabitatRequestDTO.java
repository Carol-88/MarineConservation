package marine.conservation.dto.habitat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import marine.conservation.model.Location;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitatRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private Location location;
}
