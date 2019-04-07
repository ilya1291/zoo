package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.BadRequestException;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.AnimalRepository;

import java.util.stream.Stream;

@Service
@Transactional
public class AnimalService extends BaseService<Animal> {

    private final CageService cageService;
    private final KindService kindService;
    private final KeeperService keeperService;
    private final AnimalRepository animalRepository;

    public Stream<Animal> getAllAsStream() {
        return animalRepository.findAllAsStream();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Animal create(Animal animal) {
        Kind kind = kindService.getByName(animal.getKind().getName());
        Keeper keeper = keeperService.getOne(animal.getKeeper().getId());
        animal.setKind(kind)
              .setKeeper(keeper);
        setCage(animal, animal.getCageId());
        return repository.save(animal);
    }

    public Animal moveToCage(Long cageId, Long animalId) {
        Animal animal = getOne(animalId);
        setCage(animal, cageId);
        return repository.save(animal);
    }

    private Animal setCage(Animal animal, Long cageId) {
        Cage cage = cageService.getOne(cageId);
        if (cage.isFull()) {
            throw new BadRequestException(String.format("Cage with id = %d is already full", cageId));
        }
        return animal.setCageId(cageId);
    }

    public AnimalService(JpaRepository<Animal, Long> repository,
                         CageService cageService, KindService kindService,
                         KeeperService keeperService, AnimalRepository animalRepository) {
        super(repository);
        this.cageService = cageService;
        this.kindService = kindService;
        this.keeperService = keeperService;
        this.animalRepository = animalRepository;
    }
}
