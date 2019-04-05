package ru.ilya.zoo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Ilia_Poliakov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${zoo.import.dir}")
    private String importDirectory;

    @SneakyThrows
    public String upload(InputStream fileInputStream, String originalFileName) {
        Path uploadPath = Paths.get(importDirectory + UUID.randomUUID() + originalFileName);
        if (!Files.exists(uploadPath.getParent())) {
            log.warn("directory {} is not exists and will be created", uploadPath.toAbsolutePath());
            Files.createDirectories(uploadPath.getParent());
        }
        Files.copy(fileInputStream, uploadPath);
        return uploadPath.toAbsolutePath().toString();
    }
}
