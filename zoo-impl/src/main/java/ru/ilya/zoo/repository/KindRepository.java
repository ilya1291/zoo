package ru.ilya.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.zoo.model.Kind;

import java.util.Optional;

@Repository
public interface KindRepository extends JpaRepository<Kind, Long> {

    Optional<Kind> findByNameIgnoringCase(String name);
}
