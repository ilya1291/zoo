package ru.ilya.zoo.resource;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.dto.keeper.KeeperCreateDto;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;
import ru.ilya.zoo.model.Keeper;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.keeper;

public class KeeperResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/keepers";

    @Test
    public void getAll() {
        List<KeeperResponseDto> expected = mapperFacade.mapAsList(singletonList(save(keeper())), KeeperResponseDto.class);

        ResponseEntity<List<KeeperResponseDto>> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<KeeperResponseDto>>() {}
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void getOne() {
        KeeperResponseDto expected = mapperFacade.map(save(keeper()), KeeperResponseDto.class);

        ResponseEntity<KeeperResponseDto> response = restTemplate.exchange(
                BASE_URL + "/{keeperId}",
                HttpMethod.GET,
                null,
                KeeperResponseDto.class,
                expected.getId()
        );
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnNotFound_OnGetOne() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{keeperId}",
                HttpMethod.GET,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void create() {
        KeeperCreateDto dto = new KeeperCreateDto("first_name", "last_name");
        ResponseEntity<KeeperResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                KeeperResponseDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        KeeperResponseDto result = response.getBody();

        assertNotNull(result.getId());
        assertTrue(keeperRepository.existsById(result.getId()));
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
    }

    @Test
    public void shouldNotCreateWithoutFirstName() {
        KeeperCreateDto dto = new KeeperCreateDto().setLastName("last_name");
        ResponseEntity<KeeperResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                KeeperResponseDto.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldNotCreateWithoutLastName() {
        KeeperCreateDto dto = new KeeperCreateDto().setFirstName("first_name");
        ResponseEntity<KeeperResponseDto> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                KeeperResponseDto.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteById() {
        Keeper keeperForDelete = save(keeper());
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{keeperId}",
                HttpMethod.DELETE,
                null,
                String.class,
                keeperForDelete.getId()
        );
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(keeperRepository.existsById(keeperForDelete.getId()));
    }

    @Test
    public void shouldReturnNotFound_OnDeleting() {
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/{keeperId}",
                HttpMethod.DELETE,
                null,
                String.class,
                Long.MAX_VALUE
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}