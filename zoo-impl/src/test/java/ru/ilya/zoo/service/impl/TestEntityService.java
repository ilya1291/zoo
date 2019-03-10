package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilya.zoo.model.TestEntity;

public class TestEntityService extends BaseService<TestEntity> {

    public TestEntityService(JpaRepository<TestEntity, Long> repository) {
        super(repository);
    }
}
