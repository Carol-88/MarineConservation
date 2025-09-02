package marine.conservation.dto.volunteer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import marine.conservation.model.CertifiedVolunteer;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    private String vNumber;

    @NotNull
    private CertifiedVolunteer certificate;
}
