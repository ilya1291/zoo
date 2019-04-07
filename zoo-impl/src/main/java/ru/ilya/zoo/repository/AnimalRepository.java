package ru.ilya.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ilya.zoo.model.Animal;

import java.util.stream.Stream;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a")
    Stream<Animal> findAllAsStream();
}
