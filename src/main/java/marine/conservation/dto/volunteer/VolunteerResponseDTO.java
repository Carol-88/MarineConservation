package marine.conservation.dto.volunteer;

import lombok.*;
import marine.conservation.dto.volunteer.auxiliary.EventForVolunteerDTO;
import marine.conservation.dto.volunteer.auxiliary.ProjectForVolunteerDTO;
import marine.conservation.model.CertifiedVolunteer;

import java.util.List;

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

    private List<ProjectForVolunteerDTO> projects;
    private List<EventForVolunteerDTO> events;
}
