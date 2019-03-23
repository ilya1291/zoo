package ru.ilya.zoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String searchSubject, Class entityClass) {
        this(String.format("%s with %s already exists", entityClass.getSimpleName(), searchSubject));
    }
}
