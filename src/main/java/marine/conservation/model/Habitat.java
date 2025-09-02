package marine.conservation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Embedded
    private Location location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "specie_id", nullable = false)
    @JsonIgnore
    private MarineSpecie marineSpecies;
}