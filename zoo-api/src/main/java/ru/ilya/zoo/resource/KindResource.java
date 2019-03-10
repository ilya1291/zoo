package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ilya.zoo.dto.kind.KindCreateDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Kinds", description = "Kinds resource")
@RequestMapping("api/kinds")
public interface KindResource {

    @GetMapping
    @ApiOperation("Get all kinds")
    @ResponseStatus(HttpStatus.OK)
    List<KindResponseDto> getAll();

    @GetMapping("/{kindId}")
    @ApiOperation("Get one kind by id")
    @ResponseStatus(HttpStatus.OK)
    KindResponseDto getOne(@PathVariable("kindId") Long kindId);

    @PostMapping
    @ApiOperation("Create new kind")
    @ResponseStatus(HttpStatus.CREATED)
    KindResponseDto create(@RequestBody @Valid KindCreateDto dto);

    @DeleteMapping("/{kindId}")
    @ApiOperation("Delete kind by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("kindId") Long kindId);
}
