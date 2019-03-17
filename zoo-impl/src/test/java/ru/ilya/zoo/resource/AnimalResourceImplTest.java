package ru.ilya.zoo.resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.*;

public class AnimalResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/animals";

    private Animal animal;

    @Before
    public void setUp() {
        Kind kind = save(new Kind().setName("kind1"));
        Cage cage = save(cage());
        Keeper keeper = save(keeper());
        animal = save(animal(kind, cage.getId(), keeper));
    }

    @Test
    public void getAll() {
        List<AnimalResponseDto> expected = mapperFacade.mapAsList(singletonList(animal), AnimalResponseDto.class);

        ResponseEntity<List<AnimalResponseDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AnimalResponseDto>>() {}
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getOne() {
        AnimalResponseDto expected = mapperFacade.map(animal, AnimalResponseDto.class);

        ResponseEntity<AnimalResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{animalId}",
                HttpMethod.GET,
                null,
                AnimalResponseDto.class,
                expected.getId()
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnNotFound_OnGetOne() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}",
                HttpMethod.GET,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void create() {
        Kind kind = save(kind());
        Keeper keeper = save(keeper());
        Cage cage = save(cage());

        AnimalCreateDto dto = new AnimalCreateDto()
                .setName("new_animal")
                .setBirthDate(LocalDate.now())
                .setCageId(cage.getId())
                .setKindId(kind.getId())
                .setKeeperId(keeper.getId());

        ResponseEntity<AnimalResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                AnimalResponseDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AnimalResponseDto result = response.getBody();

        assertNotNull(result.getId());
        assertTrue(animalRepository.existsById(result.getId()));
    }

    @Test
    public void shouldNotCreateAnimal_InFullCage() {
        Kind kind = save(kind());
        Keeper keeper = save(keeper());
        Cage cage = save(cage(0));

        AnimalCreateDto dto = new AnimalCreateDto()
                .setName("new_animal")
                .setBirthDate(LocalDate.now())
                .setCageId(cage.getId())
                .setKindId(kind.getId())
                .setKeeperId(keeper.getId());

        ResponseEntity<AnimalResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                AnimalResponseDto.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldMoveToCage() {
        Cage newCage = save(cage());

        ResponseEntity<AnimalResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{animalId}/cages/{cageId}",
                HttpMethod.PUT,
                null,
                AnimalResponseDto.class,
                animal.getId(), newCage.getId()
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AnimalResponseDto result = response.getBody();

        assertNotNull(result.getId());
        Animal movedAnimal = animalRepository.findById(this.animal.getId()).get();
        assertEquals(newCage.getId(), movedAnimal.getCageId());
    }

    @Test
    public void shouldNotMove_ToNotExistingCage() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}/cages/{cageId}",
                HttpMethod.PUT,
                null,
                String.class,
                animal.getId(), Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldNotMove_NotExistingAnimal() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}/cages/{cageId}",
                HttpMethod.PUT,
                null,
                String.class,
                Long.MAX_VALUE, animal.getCageId()
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldNotMoveTo_FullCage() {
        Cage newCage = save(cage(0));

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}/cages/{cageId}",
                HttpMethod.PUT,
                null,
                String.class,
                animal.getId(), newCage.getId()
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteById() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}",
                HttpMethod.DELETE,
                null,
                String.class,
                animal.getId()
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(animalRepository.existsById(animal.getId()));
    }

    @Test
    public void shouldReturnNotFound_OnDeleting() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{animalId}",
                HttpMethod.DELETE,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}