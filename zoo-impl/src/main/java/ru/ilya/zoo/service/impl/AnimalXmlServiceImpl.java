package ru.ilya.zoo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.service.AnimalXmlService;
import ru.ilya.zoo.xml.AnimalSaxHandler;

import javax.xml.parsers.SAXParser;
import java.io.InputStream;
import java.nio.file.Path;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalXmlServiceImpl implements AnimalXmlService {

    private final SAXParser saxParser;
    private final AnimalService animalService;

    @Override
    public Path exportTo() {
        return null;
    }

    @Override
    @SneakyThrows
    public void importFrom(InputStream src) {
        saxParser.parse(src, new AnimalSaxHandler(animalService::create));
    }
}
