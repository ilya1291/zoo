package ru.ilya.zoo.resource;

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

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.*;
import static ru.ilya.zoo.utils.TestObjects.keeper;

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
}