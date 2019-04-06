package ru.ilya.zoo.service.impl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import ru.ilya.zoo.model.Animal;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

public class AnimalXmlServiceImplTest  {

    private AnimalService animalService;
    private AnimalXmlServiceImpl animalXmlService;

    @Before
    public void setUp() throws Exception {
        animalService = Mockito.mock(AnimalService.class);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        animalXmlService = new AnimalXmlServiceImpl(factory.newSAXParser(), animalService);
    }

    @After
    public void tearDown() {
        reset(animalService);
    }

    @Test(expected = SAXException.class)
    public void shouldThrow_WhenImportIncorrectXml() throws IOException {
        String path = this.getClass().getResource("/IncorrectTestFile.xml").getPath();
        FileInputStream inputStream = FileUtils.openInputStream(new File(path));
        animalXmlService.importFrom(inputStream);
    }

    @Test
    public void importFrom() throws Exception {
        String path = this.getClass().getResource("/Animals.xml").getPath();
        FileInputStream inputStream = FileUtils.openInputStream(new File(path));
        animalXmlService.importFrom(inputStream);
        inputStream.close();

        Mockito.verify(animalService, times(6)).create(any(Animal.class));
    }

    @Test
    public void exportTo() throws Exception {

    }
}