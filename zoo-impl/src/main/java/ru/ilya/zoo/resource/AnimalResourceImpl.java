package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.GenericPageDto;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.service.impl.AnimalService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalResourceImpl implements AnimalResource {

    private final MapperFacade mapperFacade;
    private final AnimalService animalService;

    @Override
    public GenericPageDto<AnimalResponseDto> getAll(Pageable pageable) {
        log.debug("getAll - start");

        Page<Animal> animalPage = animalService.getAll(pageable);
        List<AnimalResponseDto> result = mapperFacade.mapAsList(animalPage.getContent(), AnimalResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return new GenericPageDto<>(result, animalPage.getTotalElements());
    }

    @Override
    public AnimalResponseDto getOne(Long animalId) {
        log.debug("getOne - start: animalId = {}", animalId);

        Animal animal = animalService.getOne(animalId);
        AnimalResponseDto result = mapperFacade.map(animal, AnimalResponseDto.class);

        log.debug("getOne - end: result = {}", result);
        return result;
    }

    @Override
    public AnimalResponseDto getRandom() {
        log.debug("getRandom - start");

        Animal animal = animalService.getRandom();
        AnimalResponseDto result = mapperFacade.map(animal, AnimalResponseDto.class);

        log.debug("getRandom - end: result = {}", result);
        return result;
    }

    @Override
    public AnimalResponseDto create(@Valid AnimalCreateDto dto) {
        log.debug("create - start: dto = {}", dto);

        Animal animal = mapperFacade.map(dto, Animal.class);
        Animal createdCage = animalService.create(animal);
        AnimalResponseDto result = mapperFacade.map(createdCage, AnimalResponseDto.class);

        log.debug("create - end: result = {}", result);
        return result;
    }

    @Override
    public AnimalResponseDto moveToCage(Long animalId, Long cageId) {
        log.debug("moveToCage - start: animalId = {}, cageId = {}", animalId, cageId);

        Animal animal = animalService.moveToCage(cageId, animalId);
        AnimalResponseDto result = mapperFacade.map(animal, AnimalResponseDto.class);

        log.debug("moveToCage - end: result = {}", result);
        return result;
    }

    @Override
    public void deleteById(Long animalId) {
        log.debug("deleteById - start: animalId = {}", animalId);

        animalService.delete(animalId);

        log.debug("deleteById - end: animalId = {}", animalId);
    }
}
