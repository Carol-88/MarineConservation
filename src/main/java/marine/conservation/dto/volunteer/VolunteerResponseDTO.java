package marine.conservation.dto.volunteer;

import lombok.*;
import marine.conservation.model.CertifiedVolunteer;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String vNumber;
    private CertifiedVolunteer certificate;
}
