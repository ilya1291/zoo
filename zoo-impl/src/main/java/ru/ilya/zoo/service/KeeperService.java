package ru.ilya.zoo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ilya.zoo.model.Keeper;

import javax.transaction.Transactional;

@Service
@Transactional
public class KeeperService extends BaseService<Keeper> {

    public KeeperService(JpaRepository<Keeper, Long> repository) {
        super(repository);
    }
}
