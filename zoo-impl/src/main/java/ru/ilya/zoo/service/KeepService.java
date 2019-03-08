package ru.ilya.zoo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.repository.KeeperRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KeepService {

    private final KeeperRepository keeperRepository;

    public List<Keeper> getAll() {
        return keeperRepository.findAll();
    }

    public Keeper create(Keeper keeper) {
        return keeperRepository.save(keeper);
    }

    public void delete(Long id) {
        if (!keeperRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Keeper.class);
        }
        keeperRepository.deleteById(id);
    }
}
