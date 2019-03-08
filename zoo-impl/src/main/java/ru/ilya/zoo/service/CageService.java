package ru.ilya.zoo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.repository.CageRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CageService {

    private final CageRepository cageRepository;

    public List<Cage> getAll() {
        return cageRepository.findAll();
    }

    public Cage create(Cage cage) {
        return cageRepository.save(cage);
    }

    public void delete(Long id) {
        if (!cageRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Cage.class);
        }
        cageRepository.deleteById(id);
    }
}
