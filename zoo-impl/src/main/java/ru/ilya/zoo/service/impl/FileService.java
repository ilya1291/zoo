package ru.ilya.zoo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${zoo.import.dir}")
    private String importDirectory;

    @SneakyThrows
    public String upload(InputStream fileInputStream, String originalFileName) {
        File destinationFile = new File(importDirectory + UUID.randomUUID() + originalFileName);
        FileUtils.copyInputStreamToFile(fileInputStream, destinationFile);
        return destinationFile.getAbsolutePath();
    }
}
