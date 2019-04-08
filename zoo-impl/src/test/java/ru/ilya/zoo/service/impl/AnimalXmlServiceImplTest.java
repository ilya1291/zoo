package ru.ilya.zoo.service.impl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.utils.TestObjects;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnimalXmlServiceImplTest  {

    private final static String TEST_EXPORT_DIR = "test_export_files";

    private File testExportFile;

    private FileService fileService;
    private AnimalService animalService;
    private AnimalXmlServiceImpl animalXmlService;

    @Before
    public void setUp() throws Exception {
        fileService = Mockito.mock(FileService.class);
        animalService = Mockito.mock(AnimalService.class);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        animalXmlService = new AnimalXmlServiceImpl(factory.newSAXParser(), fileService, animalService, XMLOutputFactory.newInstance());

        testExportFile = new File(TEST_EXPORT_DIR + "/test_export_file.xml");
        FileUtils.forceMkdirParent(testExportFile);
    }

    @After
    public void deleteTestDirectory() throws Exception {
        FileUtils.deleteDirectory(new File(testExportFile.getParent()));
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
    public void exportTo() {
        when(animalService.getAllAsStream()).thenReturn(Stream.of(TestObjects.animal()));

        File actualFile = animalXmlService.exportTo(testExportFile);

        assertTrue(actualFile.exists());
        assertThat(actualFile.length(), not(0));
    }
}