package ru.ilya.zoo.service;

import java.io.InputStream;

public interface AnimalXmlService {

    String exportTo();

    void importFrom(InputStream src);
}
