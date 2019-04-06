package ru.ilya.zoo.service;

import java.io.InputStream;
import java.nio.file.Path;

public interface AnimalXmlService {

    Path exportTo();

    void importFrom(InputStream src);
}
