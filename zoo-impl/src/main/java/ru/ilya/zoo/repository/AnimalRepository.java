package ru.ilya.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.zoo.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
