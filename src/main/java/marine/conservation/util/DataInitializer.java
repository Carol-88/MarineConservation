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

        // 1. Create Conservation Project
        ConservationProject project = ConservationProject.builder()
                .name("Mediterranean Turtle Rescue")
                .description("A long-term initiative to protect endangered turtle species across the Mediterranean Sea.")
                .initDate(LocalDate.of(2025, 7, 1))
                .finalDate(LocalDate.of(2028, 12, 31))
                .build();
        projectRepository.save(project);

        // 2. Create Marine Species
        MarineSpecie species1 = MarineSpecie.builder()
                .commonName("Loggerhead Turtle")
                .scientificName("Caretta caretta")
                .conservationStatus(ConservationStatus.ENDANGERED)
                .project(project)
                .build();

        MarineSpecie species2 = MarineSpecie.builder()
                .commonName("Bluefin Tuna")
                .scientificName("Thunnus thynnus")
                .conservationStatus(ConservationStatus.VULNERABLE)
                .project(project)
                .build();

        MarineSpecie species3 = MarineSpecie.builder()
                .commonName("Dolphin")
                .scientificName("Delphinus delphis")
                .conservationStatus(ConservationStatus.PROTECTED)
                .project(project)
                .build();

        MarineSpecie species4 = MarineSpecie.builder()
                .commonName("Coral")
                .scientificName("Corallium rubrum")
                .conservationStatus(ConservationStatus.THREATENED)
                .project(project)
                .build();

        speciesRepository.saveAll(List.of(
                species1,
                species2,
                species3,
                species4
                ));

        // 3. Create Habitat (with embedded Location)
        Location location1 = new Location("BEACH", "Mediterranean Sea", 39.5696, 2.6502); // Palma de Mallorca
        Habitat habitat = Habitat.builder()
                .name("Balearic Seagrass Beds")
                .description("Posidonia oceanica meadows vital to marine biodiversity.")
                .location(location1)
                .build();
        habitatRepository.save(habitat);

        // 4. Create Volunteer
        Volunteer volunteer = Volunteer.builder()
                .nombre("Sofia")
                .apellido("Martínez")
                .email("sofia.martinez@example.com")
                .build();
        volunteerRepository.save(volunteer);

        // 5. Create Conservation Event
        ConservationEvent event = ConservationEvent.builder()
                .name("Underwater Seagrass Monitoring")
                .description("Diving event to monitor the health of Posidonia oceanica habitats.")
                .startDateTime(LocalDateTime.of(2025, 8, 15, 10, 0))
                .endDateTime(LocalDateTime.of(2025, 8, 15, 13, 0))
                .location(Location.builder()
                        .latitude(39.1456)
                        .longitude(2.9833)
                        .build())
                .maxVolunteers(12)
                .project(project)
                .build();
        eventRepository.save(event);

        System.out.println("✅ Demo data loaded successfully.");
    }
}