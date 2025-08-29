package marine.conservation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpeciesResponseDTO {
    private Long id;
    private String commonName;
    private String scientificName;
    private String conservationStatus;
    private Long projectId;
    private String projectName; // Optional, for better UI
}