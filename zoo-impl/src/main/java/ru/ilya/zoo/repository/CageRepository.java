package ru.ilya.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.zoo.model.Cage;

@Repository
public interface CageRepository extends JpaRepository<Cage, Long> {
}
