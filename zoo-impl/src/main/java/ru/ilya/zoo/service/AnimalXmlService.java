package ru.ilya.zoo.service;

import java.io.File;
import java.io.InputStream;

public interface AnimalXmlService {

    File exportTo(File file);

    void importFrom(InputStream src);
}
