package marine.conservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "volunteer_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "volunteer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String vNumber; // Volunteer Number

    @ManyToMany(mappedBy = "volunteers")
    private Set<ConservationEvent> events = new HashSet<>();

    @ManyToMany(mappedBy = "volunteers")
    private Set<ConservationProject> projects = new HashSet<>();
}
