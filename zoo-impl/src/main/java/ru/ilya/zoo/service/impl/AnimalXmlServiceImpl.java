package ru.ilya.zoo.service.impl;

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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalXmlServiceImpl implements AnimalXmlService {

    private final SAXParser saxParser;
    private final FileService fileService;
    private final AnimalService animalService;
    private final XMLOutputFactory xmlOutputFactory;

    @Override
    @SneakyThrows
    public String exportTo() {
        File exportFile = fileService.createExportFile("animals_" + LocalDateTime.now() + ".xml");
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(exportFile), StandardCharsets.UTF_8.name());
        AnimalSaxWriter animalSaxWriter = new AnimalSaxWriter(xmlStreamWriter);

        animalSaxWriter.writeRootElement();
        try (Stream<Animal> animals = animalService.getAllAsStream()) {
            animals.forEach(animalSaxWriter::write);
        }
        animalSaxWriter.writeEndRootElement();
        animalSaxWriter.flush();

        return exportFile.getAbsolutePath();
    }

    @Override
    @SneakyThrows
    public void importFrom(InputStream src) {
        saxParser.parse(src, new AnimalSaxHandler(animalService::create));
    }
}
