package marine.conservation.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {

    private String type;      // Ejemplo: BEACH, HARBOR, OCEAN
    private String region;    // Ejemplo: "Atlantic Coast"
    private Double latitude;  // Coordenada
    private Double longitude; // Coordenada
}

