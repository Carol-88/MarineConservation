package marine.conservation.dto.habitat;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitatResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Location location;

    private List<MarineSpecie> marineSpeciesId; // ID of the related species
    private List<MarineSpecie> marineSpeciesName; // Optional: name of the species for convenience
}
