package ru.ilya.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ilya.zoo.model.utils.BidirectionalUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@SequenceGenerator(name = "kind_id_seq", sequenceName = "kind_id_seq", allocationSize = 1)
public class Kind {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kind_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    private boolean isPredator;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kind", orphanRemoval = true)
    private Collection<Animal> animals = new ArrayList<>();

    public Kind setAnimals(Collection<Animal> newAnimals) {
        return BidirectionalUtils.set(this, animals, newAnimals, Animal::setKind);
    }
}
