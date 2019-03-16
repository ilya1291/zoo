package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ilya.zoo.model.Cage;

import javax.transaction.Transactional;

@Service
@Transactional
public class CageService extends BaseService<Cage> {

    public CageService(JpaRepository<Cage, Long> repository) {
        super(repository);
    }

    public Cage getWithAnimals(Long cageId) {
        Cage cage = super.getOne(cageId);
        cage.getAnimals();
        return cage;
    }
}
