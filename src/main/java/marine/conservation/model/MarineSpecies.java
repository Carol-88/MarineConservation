package marine.conservation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marine_species")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpecies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commonName;

    @Column(nullable = false)
    private String scientificName;

    @Column(nullable = false)
    private String conservationStatus;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ConservationProject project;
}