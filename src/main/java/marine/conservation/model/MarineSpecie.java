package marine.conservation.model;

import jakarta.persistence.*;
import lombok.*;
import marine.conservation.enums.ConservationStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "marine_species")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarineSpecie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commonName;

    @Column(nullable = false)
    private String scientificName;

    @Column(nullable = false)
    private ConservationStatus conservationStatus;

    @OneToMany(mappedBy = "specie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<ConservationProject> project = new ArrayList<>();
}