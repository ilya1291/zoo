package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.zoo.validator.annotations.ValidContentType;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

//@Validated
@Api(value = "Import/Export", description = "Animals import/export resource")
@RequestMapping("api/animals")
public interface ImportExportResource {

    @ApiOperation("Import animals from xml")
    @PostMapping(value = "/import", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void upload(@RequestPart("file") @ValidContentType MultipartFile multipartFile);

    @ApiOperation("Download animals as xml")
    @GetMapping(value = "/export")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Resource> export();
}
