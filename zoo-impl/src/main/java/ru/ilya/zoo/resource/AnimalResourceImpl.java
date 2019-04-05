package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.service.impl.AnimalService;
import ru.ilya.zoo.service.impl.FileService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalResourceImpl implements AnimalResource {

    private final FileService fileService;
    private final AnimalService animalService;
    private final MapperFacade mapperFacade;

    @Override
    public List<AnimalResponseDto> getAll() {
        log.debug("getAll - start");

        List<Animal> animals = animalService.getAll();
        List<AnimalResponseDto> result = mapperFacade.mapAsList(animals, AnimalResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return result;
    }

    @Override
    public AnimalResponseDto getOne(Long animalId) {
        log.debug("getOne - start");

        Animal animal = animalService.getOne(animalId);
        AnimalResponseDto result = mapperFacade.map(animal, AnimalResponseDto.class);

        log.debug("getOne - end: result = {}", result);
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

    @Override
    public void upload(MultipartFile multipartFile) {
        log.debug("upload - start: fileName = {}", multipartFile.getOriginalFilename());

        try (InputStream fileInputStream = multipartFile.getInputStream()) {
            String result = fileService.upload(fileInputStream, multipartFile.getOriginalFilename());
            log.debug("upload - end: fileName = {}", result);
        } catch (IOException e) {
            log.error("An error has occured", e);
        }
    }
}
