package marine.conservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import marine.conservation.enums.Location;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private int maxVolunteers;
}