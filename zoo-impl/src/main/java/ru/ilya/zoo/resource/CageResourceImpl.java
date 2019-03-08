package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.service.CageService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CageResourceImpl implements CageResource {

    private final CageService cageService;
    private final MapperFacade mapperFacade;

    @Override
    public List<CageResponseDto> getAll() {
        log.debug("getAll - start");

        List<Cage> cages = cageService.getAll();
        List<CageResponseDto> result = mapperFacade.mapAsList(cages, CageResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return result;
    }

    @Override
    public CageResponseDto create(@Valid CageCreateDto dto) {
        log.debug("create - start: dto = {}", dto);

        Cage cage = mapperFacade.map(dto, Cage.class);
        Cage createdCage = cageService.create(cage);
        CageResponseDto result = mapperFacade.map(createdCage, CageResponseDto.class);

        log.debug("create - end: result = {}", result);
        return result;
    }

    @Override
    public void deleteById(Long cageId) {
        log.debug("deleteById - start: cageId = {}", cageId);

        cageService.delete(cageId);

        log.debug("deleteById - end: cageId = {}", cageId);
    }
}
