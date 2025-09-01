package marine.conservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conservation_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConservationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Embedded
    private Location location;

    @Column(nullable = false)
    private int maxVolunteers;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ConservationProject project;
}