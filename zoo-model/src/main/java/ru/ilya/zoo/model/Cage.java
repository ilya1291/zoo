package ru.ilya.zoo.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @Column(name = "is_for_predators")
    private boolean isForPredators;

    @OneToMany
    @JoinColumn(name = "cageId")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Collection<Animal> animals = new HashSet<>();

    public boolean isFull() {
        return animals.size() >= capacity;
    }
}
