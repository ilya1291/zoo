package ru.ilya.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@SequenceGenerator(name = "animal_id_seq", sequenceName = "animal_id_seq", allocationSize = 1)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "kind_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Kind kind;

    @ManyToOne
    @JoinColumn(name = "keeper_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Keeper keeper;

    @ManyToOne
    @JoinColumn(name = "cage_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cage cage;
}
