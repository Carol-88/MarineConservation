package marine.conservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
    private String vNumber;

    @Builder.Default
    @ManyToMany(mappedBy = "volunteers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<ConservationEvent> events = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "volunteers")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<ConservationProject> projects = new HashSet<>();

    // ----------------- 🔹 PreRemove Callback -----------------
    @PreRemove
    private void removeVolunteerFromAssociations() {

        for (ConservationProject project : projects) {
            project.getVolunteers().remove(this);
        }
        projects.clear();

        for (ConservationEvent event : events) {
            event.getVolunteers().remove(this);
        }
        events.clear();
    }
}