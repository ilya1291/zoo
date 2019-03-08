package ru.ilya.zoo;

import ma.glasnost.orika.MapperFacade;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ilya.zoo.config.SecurityConfig;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.AnimalRepository;
import ru.ilya.zoo.repository.CageRepository;
import ru.ilya.zoo.repository.KeeperRepository;
import ru.ilya.zoo.repository.KindRepository;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class IntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private TransactionTemplate txTemplate;

    @Autowired
    protected MapperFacade mapperFacade;

    @Autowired
    protected KeeperRepository keeperRepository;

    @Autowired
    protected CageRepository cageRepository;

    @Autowired
    protected KindRepository kindRepository;

    @Autowired
    protected AnimalRepository animalRepository;

    static {
        System.setProperty(SecurityConfig.AUTH, SecurityConfig.SECURITY_PROPERTY_OFF);
    }

    @After
    public final void clearAllRepositories() {
        animalRepository.deleteAll();
        keeperRepository.deleteAll();
        cageRepository.deleteAll();
        kindRepository.deleteAll();
    }

    public <T> T doInTransaction(Supplier<T> method) {
        return txTemplate.execute(tx -> method.get());
    }

    public void doInTransactionVoid(Runnable method) {
        txTemplate.execute(tx -> {
            method.run();
            return tx;
        });
    }

    protected Keeper save(Keeper keeper) {
        return doInTransaction(() -> keeperRepository.save(keeper));
    }
    protected Cage save(Cage cage) {
        return doInTransaction(() -> cageRepository.save(cage));
    }
    protected Kind save(Kind kind) {
        return doInTransaction(() -> kindRepository.save(kind));
    }
    protected Animal save(Animal animal) {
        return doInTransaction(() -> animalRepository.save(animal));
    }
    protected void save(Animal... animals) {
        doInTransaction(() -> animalRepository.saveAll(Arrays.asList(animals)));
    }
}
