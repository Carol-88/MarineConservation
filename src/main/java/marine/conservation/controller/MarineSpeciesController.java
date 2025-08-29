package marine.conservation.controller;

import marine.conservation.dto.MarineSpeciesRequestDTO;
import marine.conservation.dto.MarineSpeciesResponseDTO;
import marine.conservation.service.interfaces.MarineSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/species")
public class MarineSpeciesController {

    @Autowired
    private MarineSpeciesService speciesService;

    @PostMapping
    public MarineSpeciesResponseDTO create(@RequestBody MarineSpeciesRequestDTO dto) {
        return speciesService.create(dto);
    }

    @GetMapping
    public List<MarineSpeciesResponseDTO> getAll() {
        return speciesService.findAll();
    }

    @GetMapping("/{id}")
    public MarineSpeciesResponseDTO getById(@PathVariable Long id) {
        return speciesService.findById(id);
    }

    @PutMapping("/{id}")
    public MarineSpeciesResponseDTO update(@PathVariable Long id, @RequestBody MarineSpeciesRequestDTO dto) {
        return speciesService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        speciesService.delete(id);
    }
}