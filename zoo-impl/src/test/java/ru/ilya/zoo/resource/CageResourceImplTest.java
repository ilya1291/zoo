package ru.ilya.zoo.resource;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;
import ru.ilya.zoo.dto.cage.CageWithAnimalsDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestUtils.*;

public class CageResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/cages";

    @Test
    public void getAll() {
        List<CageResponseDto> expected = mapperFacade.mapAsList(singletonList(save(cage())), CageResponseDto.class);

        ResponseEntity<List<CageResponseDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CageResponseDto>>() {}
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getOne() {
        CageResponseDto expected = mapperFacade.map(save(cage()), CageResponseDto.class);

        ResponseEntity<CageResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{cageId}",
                HttpMethod.GET,
                null,
                CageResponseDto.class,
                expected.getId()
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnNotFound_OnGetOne() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{cageId}",
                HttpMethod.GET,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getWithAnimals() {
        Kind kind = save(new Kind().setName("kind1"));
        Cage cage = save(cage());
        Keeper keeper = save(keeper());
        Animal animal = save(animal(kind, cage.getId(), keeper));

        cage.setAnimals(singletonList(animal));
        CageWithAnimalsDto expected = mapperFacade.map(cage, CageWithAnimalsDto.class);

        ResponseEntity<CageWithAnimalsDto> response = restTemplate.exchange(
                BASE_URL + "/{cageId}/animals",
                HttpMethod.GET,
                null,
                CageWithAnimalsDto.class,
                cage.getId()
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void create() {
        CageCreateDto dto = new CageCreateDto(10);
        ResponseEntity<CageResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                CageResponseDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CageResponseDto result = response.getBody();

        assertNotNull(result.getId());
        assertTrue(cageRepository.existsById(result.getId()));
        assertEquals(dto.getCapacity(), result.getCapacity());
    }

    @Test
    public void deleteById() {
        Cage cageForDelete = save(new Cage());
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{cageId}",
                HttpMethod.DELETE,
                null,
                String.class,
                cageForDelete.getId()
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(cageRepository.existsById(cageForDelete.getId()));
    }

    @Test
    public void shouldReturnNotFound_OnDeleting() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{cageId}",
                HttpMethod.DELETE,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}