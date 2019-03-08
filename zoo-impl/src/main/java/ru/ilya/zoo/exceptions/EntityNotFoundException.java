package ru.ilya.zoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Long objectId, Class entityClass) {
        this("id = " + objectId, entityClass);
    }

    public EntityNotFoundException(String searchSubject, Class entityClass) {
        this(String.format("%s with %s not found", entityClass.getSimpleName(), searchSubject));
    }
}
