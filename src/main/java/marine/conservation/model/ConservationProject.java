package marine.conservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conservation_projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConservationProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    //LocalDate representa solo una fecha (año, mes, día)
    @Column(nullable = false, unique = false)
    @FutureOrPresent
    private LocalDate initDate;

    @Column(nullable = false, unique = false)
    @Future
    private LocalDate finalDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConservationEvent> events = new ArrayList<>();
}
