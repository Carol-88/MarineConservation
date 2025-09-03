package marine.conservation.util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marine.conservation.enums.ConservationStatus;
import marine.conservation.model.*;
import marine.conservation.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ConservationProjectRepository projectRepository;
    private final MarineSpecieRepository speciesRepository;
    private final HabitatRepository habitatRepository;
    private final VolunteerRepository volunteerRepository;
    private final ConservationEventRepository eventRepository;

    @Override
    @Transactional
    public void run(String... args) {

        LocalDate today = LocalDate.now();

        // ------------------------
        // 1. Crear proyectos
        // ------------------------
        ConservationProject project1 = ConservationProject.builder()
                .name("Mediterranean Turtle Rescue")
                .description("Protección de tortugas en el Mediterráneo.")
                .initDate(today.plusDays(1))
                .finalDate(today.plusYears(3))
                .build();

        ConservationProject project2 = ConservationProject.builder()
                .name("Coral Reef Conservation")
                .description("Protección de arrecifes de coral en el Caribe.")
                .initDate(today.plusDays(2))
                .finalDate(today.plusYears(2))
                .build();

        ConservationProject project3 = ConservationProject.builder()
                .name("Dolphin Monitoring Program")
                .description("Seguimiento de delfines en el Atlántico.")
                .initDate(today.plusDays(3))
                .finalDate(today.plusYears(4))
                .build();

        projectRepository.saveAll(List.of(project1, project2, project3));

        // ------------------------
        // 2. Crear especies
        // ------------------------
        MarineSpecie turtle = MarineSpecie.builder()
                .commonName("Loggerhead Turtle")
                .scientificName("Caretta caretta")
                .conservationStatus(ConservationStatus.ENDANGERED)
                .project(project1)
                .build();

        MarineSpecie coral = MarineSpecie.builder()
                .commonName("Red Coral")
                .scientificName("Corallium rubrum")
                .conservationStatus(ConservationStatus.THREATENED)
                .project(project2)
                .build();

        MarineSpecie dolphin = MarineSpecie.builder()
                .commonName("Common Dolphin")
                .scientificName("Delphinus delphis")
                .conservationStatus(ConservationStatus.PROTECTED)
                .project(project3)
                .build();

        speciesRepository.saveAll(List.of(turtle, coral, dolphin));

        // ------------------------
        // 3. Crear hábitats
        // ------------------------
        Habitat habitat1 = Habitat.builder()
                .name("Balearic Seagrass Beds")
                .description("Posidonia oceanica meadows vitales para la biodiversidad.")
                .marineSpecies(turtle)
                .location(new Location("BEACH", "Mediterranean Sea", 39.5696, 2.6502))
                .build();

        Habitat habitat2 = Habitat.builder()
                .name("Caribbean Coral Reef")
                .description("Arrecife de coral con alta biodiversidad.")
                .marineSpecies(coral)
                .location(new Location("REEF", "Caribbean Sea", 18.2208, -66.5901))
                .build();

        Habitat habitat3 = Habitat.builder()
                .name("Atlantic Dolphin Bay")
                .description("Zona de observación de delfines.")
                .marineSpecies(dolphin)
                .location(new Location("BAY", "Atlantic Ocean", 36.7783, -25.4244))
                .build();

        habitatRepository.saveAll(List.of(habitat1, habitat2, habitat3));

        // ------------------------
        // 4. Crear voluntarios
        // ------------------------
        Volunteer v1 = Volunteer.builder()
                .name("Sofia Martinez")
                .email("sofia.martinez@example.com")
                .phone("+34-600-123-456")
                .vNumber("VOL-001")
                .build();

        Volunteer v2 = Volunteer.builder()
                .name("Carlos Lopez")
                .email("carlos.lopez@example.com")
                .phone("+34-600-654-321")
                .vNumber("VOL-002")
                .build();

        Volunteer v3 = Volunteer.builder()
                .name("Ana Torres")
                .email("ana.torres@example.com")
                .phone("+34-601-111-222")
                .vNumber("VOL-003")
                .build();

        Volunteer v4 = Volunteer.builder()
                .name("Miguel Fernandez")
                .email("miguel.fernandez@example.com")
                .phone("+34-602-333-444")
                .vNumber("VOL-004")
                .build();

        Volunteer v5 = Volunteer.builder()
                .name("Lucia Gomez")
                .email("lucia.gomez@example.com")
                .phone("+34-603-555-666")
                .vNumber("VOL-005")
                .build();

        Volunteer v6 = Volunteer.builder()
                .name("Javier Ruiz")
                .email("javier.ruiz@example.com")
                .phone("+34-604-777-888")
                .vNumber("VOL-006")
                .build();

        volunteerRepository.saveAll(List.of(v1, v2, v3, v4, v5, v6));

        // ------------------------
        // 5. Link voluntarios a proyectos
        // ------------------------
        project1.getVolunteers().add(v1);
        project1.getVolunteers().add(v2);
        v1.getProjects().add(project1);
        v2.getProjects().add(project1);

        project2.getVolunteers().add(v3);
        project2.getVolunteers().add(v4);
        v3.getProjects().add(project2);
        v4.getProjects().add(project2);

        project3.getVolunteers().add(v5);
        project3.getVolunteers().add(v6);
        v5.getProjects().add(project3);
        v6.getProjects().add(project3);

        projectRepository.saveAll(List.of(project1, project2, project3));
        volunteerRepository.saveAll(List.of(v1, v2, v3, v4, v5, v6));

        // ------------------------
        // 6. Crear eventos y asignar voluntarios
        // ------------------------
        ConservationEvent e1 = ConservationEvent.builder()
                .name("Turtle Nesting Monitoring")
                .description("Monitoreo de nidos de tortugas en la playa.")
                .startDateTime(LocalDateTime.of(today.getYear(), 10, 15, 8, 0))
                .endDateTime(LocalDateTime.of(today.getYear(), 12, 15, 12, 0))
                .location(new Location("BEACH", "Mediterranean Sea", 39.5696, 2.6502))
                .maxVolunteers(5)
                .project(project1)
                .build();

        ConservationEvent e2 = ConservationEvent.builder()
                .name("Coral Cleaning")
                .description("Limpieza y monitoreo de arrecifes de coral.")
                .startDateTime(LocalDateTime.of(today.getYear(), 10, 10, 9, 0))
                .endDateTime(LocalDateTime.of(today.getYear(), 12, 10, 13, 0))
                .location(new Location("REEF", "Caribbean Sea", 18.2208, -66.5901))
                .maxVolunteers(6)
                .project(project2)
                .build();

        ConservationEvent e3 = ConservationEvent.builder()
                .name("Dolphin Tracking")
                .description("Observación de delfines en la bahía.")
                .startDateTime(LocalDateTime.of(today.getYear(), 10, 20, 10, 0))
                .endDateTime(LocalDateTime.of(today.getYear(), 12, 20, 14, 0))
                .location(new Location("BAY", "Atlantic Ocean", 36.7783, -25.4244))
                .maxVolunteers(4)
                .project(project3)
                .build();

        eventRepository.saveAll(List.of(e1, e2, e3));

        // Asignar voluntarios a eventos
        e1.getVolunteers().add(v1);
        e1.getVolunteers().add(v2);
        v1.getEvents().add(e1);
        v2.getEvents().add(e1);

        e2.getVolunteers().add(v3);
        e2.getVolunteers().add(v4);
        v3.getEvents().add(e2);
        v4.getEvents().add(e2);

        e3.getVolunteers().add(v5);
        e3.getVolunteers().add(v6);
        v5.getEvents().add(e3);
        v6.getEvents().add(e3);

        volunteerRepository.saveAll(List.of(v1, v2, v3, v4, v5, v6));
        eventRepository.saveAll(List.of(e1, e2, e3));

        System.out.println("\n✅ Demo data cargada con múltiples proyectos, voluntarios y eventos correctamente.\n");
    }
}