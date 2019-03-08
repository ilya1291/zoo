package ru.ilya.zoo.model;

import lombok.Data;
import ru.ilya.zoo.model.utils.BidirectionalUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Entity
@SequenceGenerator(name = "cage_id_seq", sequenceName = "cage_id_seq", allocationSize = 1)
public class Cage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cage_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "cage")
    private Collection<Animal> animals = new HashSet<>();

    public Cage setAnimals(Collection<Animal> newAnimals) {
        return BidirectionalUtils.set(this, animals, newAnimals, Animal::setCage);
    }
}
