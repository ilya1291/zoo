package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.zoo.service.AnimalXmlService;
import ru.ilya.zoo.service.impl.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImportExportResourceImpl implements ImportExportResource {

    private final FileService fileService;
    private final AnimalXmlService animalXmlService;

    @Override
    public void upload(MultipartFile multipartFile) {
        log.debug("upload - start: fileName = {}", multipartFile.getOriginalFilename());

        String uploadedFile = fileService.upload(multipartFile);
        try (InputStream inputStream = FileUtils.openInputStream(new File(uploadedFile))) {
            animalXmlService.importFrom(inputStream);
        } catch (IOException e) {
            log.error("An error has occurred during xml reading. File = {}", uploadedFile);
            log.error("An error: ", e);
        }

        log.debug("upload - end: uploadedFile = {}", uploadedFile);
    }

    @Override
    public ResponseEntity<Resource> export() {
        log.debug("upload - start");

        File file = new File(animalXmlService.exportTo());

        log.debug("upload - end");
        return ResponseEntity.ok()
                .headers(downloadingHttpHeaders(file))
                .body(new FileSystemResource(file));
    }

    private HttpHeaders downloadingHttpHeaders(File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.length());
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename(file.getName())
                                                        .build());
        return headers;
    }
}
