package marine.conservation.dto.marineSpecie;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpecieResponseDTO {
    private Long id;
    private String commonName;
    private String scientificName;
    private String conservationStatus;
    private Long projectId;
}