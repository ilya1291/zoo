package ru.ilya.zoo.utils;

import lombok.experimental.UtilityClass;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.time.LocalDate;

@UtilityClass
public class TestUtils {

    public static Animal animal() {
        return new Animal()
                .setId(1L)
                .setName("name")
                .setBirthDate(LocalDate.now())
                .setCageId(1L)
                .setKeeper(keeper())
                .setKind(kind());
    }

    public static Animal animal(String name, Kind kind, Long cageId, Keeper keeper) {
        return new Animal()
                .setName(name)
                .setBirthDate(LocalDate.now())
                .setKind(kind)
                .setCageId(cageId)
                .setKeeper(keeper);
    }

    public static Animal animal(Kind kind, Long cageId, Keeper keeper) {
        return animal("name", kind, cageId, keeper);
    }

    public static Kind kind() {
        return kind("default_name");
    }

    public static Kind kind(String name) {
        return new Kind().setName(name);
    }

    public static Kind kindPredator(String name) {
        return kind(name).setPredator(true);
    }

    public static Keeper keeper() {
        return new Keeper()
                .setFirstName("first_name")
                .setLastName("last_name");
    }

    public static Cage cage() {
        return cage(10);
    }

    public static Cage cage(Integer capacity) {
        return new Cage().setCapacity(capacity);
    }
}
