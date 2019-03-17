package ru.ilya.zoo.resource;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.auth.JwtDto;
import ru.ilya.zoo.dto.auth.LoginDto;
import ru.ilya.zoo.dto.auth.SignUpDto;
import ru.ilya.zoo.dto.auth.UserDto;
import ru.ilya.zoo.model.User;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.user;

public class AuthResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/auth";

    @Test
    public void signUp() {
        SignUpDto dto = new SignUpDto()
                .setUsername("new_user")
                .setEmail("new_user@email.com")
                .setPassword("password");

        ResponseEntity<UserDto> response = restTemplate.exchange(
                BASE_URL + "/signup",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                UserDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        UserDto result = response.getBody();

        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getUsername(), result.getUsername());
        assertTrue(userRepository.existsByEmail(dto.getEmail()));
        assertTrue(userRepository.existsByUsername(dto.getUsername()));
    }

    @Test
    public void signIn() {
        String passwordHash = "$2a$10$J6/jkT8ETTEBBmCc.zPdM./96p55wzqOvqcl.rwLHkr0/fj3J7Hn6";
        User user = save(user("existing_user", "user@email.com").setPassword(passwordHash));

        LoginDto dto = new LoginDto(user.getUsername(), "password");
        ResponseEntity<JwtDto> response = restTemplate.exchange(
                BASE_URL + "/signin",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                JwtDto.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtDto result = response.getBody();
        assertNotNull(result);
        assertTrue(StringUtils.hasText(result.getAccessToken()));
    }

    @Test
    public void shouldNotLogin() {
        LoginDto dto = new LoginDto("non_registered_user", "password");
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/signin",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                String.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}