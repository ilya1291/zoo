package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ilya.zoo.dto.keeper.KeeperCreateDto;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Keepers", description = "Keepers resource")
@RequestMapping("api/keepers")
public interface KeeperResource {

    @GetMapping
    @ApiOperation("Get all keepers")
    @ResponseStatus(HttpStatus.OK)
    List<KeeperResponseDto> getAll();

    @PostMapping
    @ApiOperation("Create new keeper")
    @ResponseStatus(HttpStatus.CREATED)
    KeeperResponseDto create(@RequestBody @Valid KeeperCreateDto dto);

    @DeleteMapping("/{keeperId}")
    @ApiOperation("Delete keeper by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("keeperId") Long keeperId);
}
