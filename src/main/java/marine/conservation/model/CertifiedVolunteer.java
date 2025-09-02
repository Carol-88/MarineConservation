package marine.conservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import marine.conservation.enums.CertificationType;

@Entity
@DiscriminatorValue("CERTIFIED")
@Data
@NoArgsConstructor
public class CertifiedVolunteer extends Volunteer {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificationType certification;
}

