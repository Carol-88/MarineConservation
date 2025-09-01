package marine.conservation.model;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;

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
    private Location location; // embedded lat/long per spec

    // Many-to-One relationship with Specie
    @ManyToOne
    @JoinColumn(name = "specie_id", nullable = false)
    private MarineSpecie marineSpecies;
}
