package ru.ilya.zoo.service.impl;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.service.AnimalXmlService;
import ru.ilya.zoo.xml.AnimalSaxHandler;
import ru.ilya.zoo.xml.AnimalSaxWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalXmlServiceImpl implements AnimalXmlService {

    private final SAXParser saxParser;
    private final AnimalService animalService;
    private final XMLOutputFactory xmlOutputFactory;

    @Override
    @SneakyThrows
    public File exportTo(File file) {
        @Cleanup FileOutputStream outputStream = new FileOutputStream(file);
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream, UTF_8.name());
        @Cleanup AnimalSaxWriter animalSaxWriter = new AnimalSaxWriter(xmlStreamWriter);

        animalSaxWriter.writeRootElement();
        try (Stream<Animal> animals = animalService.getAllAsStream()) {
            animals.forEach(animalSaxWriter::write);
        }
        animalSaxWriter.writeEndRootElement();

        return file;
    }

    @Override
    @SneakyThrows
    public void importFrom(InputStream src) {
        saxParser.parse(src, new AnimalSaxHandler(animalService::create));
    }
}
