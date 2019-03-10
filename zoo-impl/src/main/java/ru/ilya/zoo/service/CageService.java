package ru.ilya.zoo.service;

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
}
