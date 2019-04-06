package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.KindRepository;

@Service
@Transactional
public class KindService extends BaseService<Kind> {

    private final KindRepository kindRepository;

    public Kind getByName(String name) {
        return kindRepository.findByNameIgnoringCase(name)
                .orElseThrow(() -> new EntityNotFoundException(name, Kind.class));
    }

    public KindService(JpaRepository<Kind, Long> repository, KindRepository kindRepository) {
        super(repository);
        this.kindRepository = kindRepository;
    }
}
