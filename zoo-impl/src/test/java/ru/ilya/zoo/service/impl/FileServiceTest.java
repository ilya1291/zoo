package ru.ilya.zoo.service.impl;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class FileServiceTest {

    private final static byte[] dummyFile = new byte[1024];
    private static final String TEMP_DIRECTORY = "import-files/";

    private FileService fileService;

    @Before
    public void setUp() {
        new Random().nextBytes(dummyFile);
        fileService = new FileService();
        ReflectionTestUtils.setField(fileService, "importDirectory", TEMP_DIRECTORY);
    }

    @After
    public void clearDirectory() throws Exception {
        FileUtils.deleteDirectory(new File(TEMP_DIRECTORY));
    }

    @Test
    public void shouldUploadFile() {
        ByteArrayInputStream in = new ByteArrayInputStream(dummyFile);
        File actualFile = new File(fileService.upload(in, "1.txt"));

        assertTrue(actualFile.exists());
        assertEquals(dummyFile.length, actualFile.length());
    }

    @Test(expected = IOException.class)
    public void shouldThrow() throws Exception {
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        Mockito.when(mockInputStream.read(any())).thenThrow(IOException.class);
        fileService.upload(mockInputStream, "1.txt");
    }
}