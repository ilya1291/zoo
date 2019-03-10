package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.model.Kind;

@Service
@Transactional
public class KindService extends BaseService<Kind> {

    public KindService(JpaRepository<Kind, Long> repository) {
        super(repository);
    }
}
