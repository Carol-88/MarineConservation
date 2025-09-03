package marine.conservation.dto.volunteer.aux;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDTO {
    private String type;
    private String region;
    private double latitude;
    private double longitude;
}
