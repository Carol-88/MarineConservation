package marine.conservation.dto.marineSpecie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import marine.conservation.enums.ConservationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpecieRequestDTO {

    @NotNull
    @NotBlank(message = "The common name cannot be blank")
    @Size(max = 25)
    private String commonName;

    @NotNull
    @Size(max = 50)
    @NotBlank(message = "The scientific name cannot be blank")
    private String scientificName;

    @NotNull
    private ConservationStatus conservationStatus;

}