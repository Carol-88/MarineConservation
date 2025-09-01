package marine.conservation.service.impl;

import lombok.RequiredArgsConstructor;
import marine.conservation.model.Habitat;
import marine.conservation.repository.HabitatRepository;
import marine.conservation.service.interfaces.HabitatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitatServiceImpl implements HabitatService {

    private final HabitatRepository habitatRepository;

    @Override
    public Habitat save(Habitat habitat) {
        return habitatRepository.save(habitat);
    }

    @Override
    public Habitat findById(Long id) {
        return habitatRepository.findById(id).orElse(null);
    }

    @Override
    public List<Habitat> findAll() {
        return habitatRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        habitatRepository.deleteById(id);
    }

}
