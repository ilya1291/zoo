package ru.ilya.zoo.utils;

import lombok.experimental.UtilityClass;
import ru.ilya.zoo.model.*;

import java.time.LocalDate;
import java.util.Collection;

@UtilityClass
public class TestObjects {

    public static User user(String username, String email, String password, Collection<Role> roles) {
        return user(username, email)
                .setPassword(password)
                .setRoles(roles);
    }

    public static User user(String username, String email) {
        return new User()
                .setUsername(username)
                .setEmail(email)
                .setPassword("password");
    }

    public static User user(long id) {
        return user().setId(id);
    }

    public static User user() {
        return user("user", "user@email.com");
    }

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

    public static Cage cageForPredators(Integer capacity) {
        return cage(capacity).setForPredators(true);
    }
}
