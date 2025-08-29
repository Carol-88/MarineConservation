package marine.conservation.controller;

import jakarta.validation.Valid;
import marine.conservation.dto.marineSpecie.MarineSpecieRequestDTO;
import marine.conservation.dto.marineSpecie.MarineSpecieResponseDTO;
import marine.conservation.service.interfaces.MarineSpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/species")
public class MarineSpecieController {

    @Autowired
    private MarineSpecieService specieService;

    @PostMapping
    public MarineSpecieResponseDTO create(@Valid @RequestBody MarineSpecieRequestDTO dto) {
        return specieService.create(dto);
    }

    @GetMapping
    public List<MarineSpecieResponseDTO> getAll() {
        return specieService.findAll();
    }

    @GetMapping("/{id}")
    public MarineSpecieResponseDTO getById(@PathVariable Long id) {
        return specieService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        specieService.delete(id);
    }
}