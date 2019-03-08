package ru.ilya.zoo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.KindRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KindService {

    private final KindRepository kindRepository;

    public List<Kind> getAll() {
        return kindRepository.findAll();
    }

    public Kind create(Kind kind) {
        return kindRepository.save(kind);
    }

    public void delete(Long id) {
        if (!kindRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Kind.class);
        }
        kindRepository.deleteById(id);
    }
}
