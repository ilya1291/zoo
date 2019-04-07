package ru.ilya.zoo.resource;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.*;

public class ImportExportResourceImplTest extends IntegrationTest {

    private static final String BASE_URL = "/api/animals";

    @Autowired
    private EntityManager entityManager;

    @After
    public void resetSequences() {
        doInTransactionVoid(() -> {
            entityManager.createNativeQuery(
                    "ALTER SEQUENCE keeper_id_seq RESTART WITH 1")
                    .executeUpdate();
            entityManager.createNativeQuery(
                    "ALTER SEQUENCE cage_id_seq RESTART WITH 1")
                    .executeUpdate();
        });
    }

    @Test
    public void shouldUploadFile() {
        clearDb();
        doInTransactionVoid(this::resetSequences);
        save(kind("Hare"));
        save(kind("Wolf"));
        save(cage());
        save(cage());
        save(keeper());
        save(keeper());

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new FileSystemResource(this.getClass().getResource("/Animals.xml").getPath()));

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/import",
                HttpMethod.POST,
                new HttpEntity<>(parameters),
                String.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldReturnBadRequest_WhenUploadingNotSupportedFile() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new FileSystemResource(this.getClass().getResource("/WrongTypeFile.txt").getPath()));

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/import",
                HttpMethod.POST,
                new HttpEntity<>(parameters),
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldDownloadAnimals() {
        Kind kind = save(new Kind().setName("kind1"));
        Cage cage = save(cage());
        Keeper keeper = save(keeper());
        Animal animal = save(animal(kind, cage.getId(), keeper));

        ResponseEntity<byte[]> result = restTemplate.execute(
                BASE_URL + "/export",
                HttpMethod.GET,
                req -> {},
                resp -> new ResponseEntity<>(IOUtils.toByteArray(resp.getBody()), resp.getStatusCode())
        );
        assertEquals(result.getStatusCode(), HttpStatus.OK);

        String resultXml = new String(result.getBody());
        assertFalse(resultXml.trim().isEmpty());
    }
}