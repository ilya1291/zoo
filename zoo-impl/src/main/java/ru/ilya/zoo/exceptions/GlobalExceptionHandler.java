package ru.ilya.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xml.sax.SAXException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SAXException.class)
    public ResponseEntity handleSAXException(SAXException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity("Xml file is not valid", HttpStatus.BAD_REQUEST);
    }
}
