package ru.ilya.zoo.repository;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.User;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.user;

public class UserRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        User user = user("user1", "user1@email.com");
        user = userRepository.save(user);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowOnSave_whenLoginIsNull() {
        User user = user(null, "user1@email.com");
        userRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowOnSave_whenEmailIsNull() {
        User user = user("user1", null);
        userRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowOnSave_whenPasswordIsNull() {
        User user = user().setPassword(null);
        userRepository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrow_OnSaveDuplicatedLogin() {
        User user1 = user("user1", "user1@email.com");
        User user2 = user("user1", "user2@email.com");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrow_OnSaveDuplicatedEmail() {
        User user1 = user("user1", "user1@email.com");
        User user2 = user("user2", "user1@email.com");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void shouldGetUserByUsername() {
        User user = user("user1", "user1@email.com");
        user = userRepository.save(user);

        User result = userRepository.findByUsernameOrEmail(user.getUsername()).get();

        assertNotNull(result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void shouldGetUserByEmail() {
        User user = user("user1", "user1@email.com");
        user = userRepository.save(user);

        User result = userRepository.findByUsernameOrEmail(user.getEmail()).get();

        assertNotNull(result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    public void shouldExistsByUsername() {
        User user = userRepository.save(user());
        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    public void shouldExistsByEmail() {
        User user = userRepository.save(user());
        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }
}