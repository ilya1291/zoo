package ru.ilya.zoo.resource;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.kind.KindCreateDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;
import ru.ilya.zoo.model.Kind;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.kind;

public class KindResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/kinds";

    @Test
    public void getAll() {
        List<KindResponseDto> expected = mapperFacade.mapAsList(singletonList(save(kind())), KindResponseDto.class);

        ResponseEntity<List<KindResponseDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<KindResponseDto>>() {}
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getOne() {
        KindResponseDto expected = mapperFacade.map(save(kind()), KindResponseDto.class);

        ResponseEntity<KindResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{kindId}",
                HttpMethod.GET,
                null,
                KindResponseDto.class,
                expected.getId()
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnNotFound_OnGetOne() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{kindId}",
                HttpMethod.GET,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void create() {
        KindCreateDto dto = new KindCreateDto("name", true);
        ResponseEntity<KindResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                KindResponseDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        KindResponseDto result = response.getBody();

        assertNotNull(result.getId());
        assertTrue(kindRepository.existsById(result.getId()));
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.isPredator(), result.isPredator());
    }

    @Test
    public void shouldNotCreateWithoutName() {
        KindCreateDto dto = new KindCreateDto("", true);
        ResponseEntity<KindResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                KindResponseDto.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteById() {
        Kind kindForDelete = save(kind());
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{kindId}",
                HttpMethod.DELETE,
                null,
                String.class,
                kindForDelete.getId()
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(kindRepository.existsById(kindForDelete.getId()));
    }

    @Test
    public void shouldReturnNotFound_OnDeleting() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{kindId}",
                HttpMethod.DELETE,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}