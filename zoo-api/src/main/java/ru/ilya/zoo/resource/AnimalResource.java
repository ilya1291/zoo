package ru.ilya.zoo.resource;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Animals", description = "Animals resource")
@RequestMapping("api/animals")
public interface AnimalResource {

    @GetMapping
    @ApiOperation("Get all animals")
    @ResponseStatus(HttpStatus.OK)
    List<AnimalResponseDto> getAll();

    @GetMapping("/{animalId}")
    @ApiOperation("Get one animal by id")
    @ResponseStatus(HttpStatus.OK)
    AnimalResponseDto getOne(@PathVariable("animalId") Long animalId);

    @PostMapping
    @ApiOperation("Create new animal")
    @ResponseStatus(HttpStatus.CREATED)
    AnimalResponseDto create(@RequestBody @Valid AnimalCreateDto dto);

    @PutMapping("/{animalId}/cages/{cageId}")
    @ApiOperation("Move animal to another cage")
    @ResponseStatus(HttpStatus.OK)
    AnimalResponseDto moveToCage(@PathVariable("animalId") Long animalId,
                                 @PathVariable("cageId") Long cageId);

    @DeleteMapping("/{animalId}")
    @ApiOperation("Delete animal by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("animalId") Long animalId);

    @ApiOperation("Import animals from xml")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    void upload(@RequestPart("file") MultipartFile multipartFile);
}
