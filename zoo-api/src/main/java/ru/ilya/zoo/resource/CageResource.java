package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Cages", description = "Cages resource")
@RequestMapping("api/cages")
public interface CageResource {

    @GetMapping
    @ApiOperation("Get all cages")
    @ResponseStatus(HttpStatus.OK)
    List<CageResponseDto> getAll();

    @GetMapping("/{cageId}")
    @ApiOperation("Get one cage by id")
    @ResponseStatus(HttpStatus.OK)
    CageResponseDto getOne(@PathVariable("cageId") Long cageId);

    @GetMapping("/{cageId}/animals")
    @ApiOperation("Get cage by id with animals")
    @ResponseStatus(HttpStatus.OK)
    CageResponseDto getWithAnimals(@PathVariable("cageId") Long cageId);

    @PostMapping
    @ApiOperation("Create new cage")
    @ResponseStatus(HttpStatus.CREATED)
    CageResponseDto create(@RequestBody @Valid CageCreateDto dto);

    @DeleteMapping("/{cageId}")
    @ApiOperation("Delete cage by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("cageId") Long cageId);
}
