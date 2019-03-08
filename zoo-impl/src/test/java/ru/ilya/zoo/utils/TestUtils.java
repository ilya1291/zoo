package ru.ilya.zoo.utils;

import lombok.experimental.UtilityClass;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.time.LocalDate;

@UtilityClass
public class TestUtils {

    public static Kind kind(String name) {
        return new Kind().setName(name);
    }

    public static Kind kindPredator(String name) {
        return kind(name).setPredator(true);
    }

    public static Animal animal(String name, Kind kind, Cage cage, Keeper keeper) {
        return new Animal()
                .setName(name)
                .setBirthDate(LocalDate.now())
                .setKind(kind)
                .setCage(cage)
                .setKeeper(keeper);
    }

    public static Keeper keeper() {
        return new Keeper()
                .setFirstName("first_name")
                .setLastName("last_name");
    }
}
