package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.BadRequestException;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

@Service
@Transactional
public class AnimalService extends BaseService<Animal> {

    private final CageService cageService;
    private final KindService kindService;
    private final KeeperService keeperService;

    @Override
    public Animal create(Animal animal) {
        Cage cage = cageService.getOne(animal.getCageId());
        Kind kind = kindService.getOne(animal.getKind().getId());
        Keeper keeper = keeperService.getOne(animal.getKeeper().getId());

        animal.setKind(kind)
                .setCageId(cage.getId())
                .setKeeper(keeper);

        return repository.save(animal);
    }

    public Animal moveToCage(Long cageId, Long animalId) {
        Cage cage = cageService.getOne(cageId);
        if (cage.isFull()) {
            throw new BadRequestException();
        }
        Animal animal = getOne(animalId);
        animal.setCageId(cageId);
        return repository.save(animal);
    }

    public AnimalService(JpaRepository<Animal, Long> repository,
                         CageService cageService, KindService kindService,
                         KeeperService keeperService) {
        super(repository);
        this.cageService = cageService;
        this.kindService = kindService;
        this.keeperService = keeperService;
    }
}
