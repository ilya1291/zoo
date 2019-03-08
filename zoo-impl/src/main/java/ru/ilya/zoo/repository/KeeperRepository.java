package ru.ilya.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.zoo.model.Keeper;

@Repository
public interface KeeperRepository extends JpaRepository<Keeper, Long> {
}
