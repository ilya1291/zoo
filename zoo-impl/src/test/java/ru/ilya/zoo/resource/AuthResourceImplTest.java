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
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.user;

public class AuthResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/auth";

    @Test
    public void login() {
        final String password = "123456";
        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
        User user = save(user("admin", "admin@zoo.com",passwordEncoder.encode(password), singletonList(roleAdmin)));

        LoginDto dto = new LoginDto(user.getUsername(), password);
        ResponseEntity<JwtDto> response = restTemplate.exchange(
                BASE_URL + "/login",
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
                BASE_URL + "/login",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                String.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}