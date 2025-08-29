package marine.conservation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpeciesRequestDTO {
    private String commonName;
    private String scientificName;
    private String conservationStatus;
    private Long projectId;
}