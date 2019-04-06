package ru.ilya.zoo.service.impl;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String upload(MultipartFile multipartFile) {
        @Cleanup InputStream fileInputStream = multipartFile.getInputStream();
        File destinationFile = new File(importDirectory + UUID.randomUUID() + multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(fileInputStream, destinationFile);
        return destinationFile.getAbsolutePath();
    }
}
