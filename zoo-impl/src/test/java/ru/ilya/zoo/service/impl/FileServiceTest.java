package ru.ilya.zoo.service.impl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileServiceTest {

    private final static byte[] dummyFile = new byte[1024];
    private static final String TEMP_IMPORT_DIRECTORY = "import-files/";
    private static final String TEMP_EXPORT_DIRECTORY = "export-files/";

    private FileService fileService;

    @Before
    public void setUp() {
        new Random().nextBytes(dummyFile);
        fileService = new FileService();
        ReflectionTestUtils.setField(fileService, "importDirectory", TEMP_IMPORT_DIRECTORY);
        ReflectionTestUtils.setField(fileService, "exportDirectory", TEMP_EXPORT_DIRECTORY);
    }

    @After
    public void clearDirectory() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_IMPORT_DIRECTORY));
        FileUtils.deleteDirectory(new File(TEMP_EXPORT_DIRECTORY));
    }

    @Test
    public void shouldUploadFile() {
        MultipartFile multipartFile = new MockMultipartFile("file", dummyFile);
        File actualFile = new File(fileService.upload(multipartFile));

        assertTrue(actualFile.exists());
        assertEquals(dummyFile.length, actualFile.length());
    }

    @Test(expected = IOException.class)
    public void shouldThrow_onGettingInputStream() throws Exception {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.getInputStream()).thenThrow(IOException.class);
        fileService.upload(multipartFile);
    }

    @Test(expected = IOException.class)
    public void shouldThrow_onClosingInputStream() throws Exception {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.doThrow(IOException.class).when(inputStream).close();
        Mockito.when(inputStream.read(ArgumentMatchers.any())).thenReturn(-1);
        Mockito.when(multipartFile.getInputStream()).thenReturn(inputStream);

        fileService.upload(multipartFile);
    }

    @Test
    public void shouldCreateExportFile() {
        File testFile = fileService.createExportFile("test-file.txt");
        assertTrue(testFile.exists());
        assertEquals(TEMP_EXPORT_DIRECTORY, testFile.getParent() + "/");
    }
}