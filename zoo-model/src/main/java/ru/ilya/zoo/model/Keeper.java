package ru.ilya.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ilya.zoo.model.utils.BidirectionalUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Entity
@SequenceGenerator(name = "keeper_id_seq", sequenceName = "keeper_id_seq", allocationSize = 1)
public class Keeper {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "keeper_id_seq")
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "keeper")
    private Collection<Animal> animals = new HashSet<>();

    public Keeper setAnimals(Collection<Animal> newAnimals) {
        return BidirectionalUtils.set(this, animals, newAnimals, Animal::setKeeper);
    }

}
