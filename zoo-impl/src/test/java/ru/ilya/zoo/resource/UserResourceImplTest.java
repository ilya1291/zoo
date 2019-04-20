package ru.ilya.zoo.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.user.UserRegisterDto;
import ru.ilya.zoo.dto.user.UserResponseDto;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;

import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.user;

public class UserResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/admin/users";
    private static final String PASSWORD = "123456";

    @Value("${jwt.secret}")
    private String jwtSecret;

    private User admin;
    private HttpHeaders authHeader;

    @Before
    public void setUp() {
        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
        admin = save(user("admin", "admin@zoo.com", passwordEncoder.encode(PASSWORD), singletonList(roleAdmin)));
        String token = Jwts.builder()
                .setSubject(Long.toString(admin.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(Long.MAX_VALUE))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        this.authHeader = headers;
    }

    @Test
    public void getAll() {
        UserResponseDto expected = mapperFacade.map(admin, UserResponseDto.class);

        ResponseEntity<List<UserResponseDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                new HttpEntity<>(authHeader),
                new ParameterizedTypeReference<List<UserResponseDto>>() {}
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().contains(expected));
    }

    @Test
    public void shouldRegisterNewUser() {
        UserRegisterDto dto = new UserRegisterDto()
                .setUsername("manager")
                .setEmail("manager@zoo.com")
                .setPassword(PASSWORD);

        ResponseEntity<UserResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto, authHeader),
                UserResponseDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        UserResponseDto result = response.getBody();

        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getUsername(), result.getUsername());
        assertTrue(userRepository.existsByEmail(dto.getEmail()));
        assertTrue(userRepository.existsByUsername(dto.getUsername()));
    }

    @Test
    public void shouldAssignRole() {
        User user = save(user("user", "user@zoo.com", passwordEncoder.encode(PASSWORD), emptyList()));
        Role expectedRole = roleRepository.findByName(RoleName.ROLE_MANAGER).get();
        ResponseEntity<UserResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{userId}/roles/{roleName}",
                HttpMethod.PUT,
                new HttpEntity<>(authHeader),
                UserResponseDto.class,
                user.getId(), RoleName.ROLE_MANAGER.name()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        User result = userRepository.findById(user.getId()).get();
        assertTrue(result.getRoles().contains(expectedRole));
    }

    @Test
    public void deleteById() {
        User user = save(user("user", "user@zoo.com", "password", emptyList()));
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(authHeader),
                String.class,
                user.getId()
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    public void shouldReturnNotFound_OnDeleting() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(authHeader),
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}