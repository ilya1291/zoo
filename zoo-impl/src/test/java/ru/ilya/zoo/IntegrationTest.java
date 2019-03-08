package ru.ilya.zoo;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ilya.zoo.repository.AnimalRepository;
import ru.ilya.zoo.repository.CageRepository;
import ru.ilya.zoo.repository.KeeperRepository;
import ru.ilya.zoo.repository.KindRepository;

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
    protected KeeperRepository keeperRepository;

    @Autowired
    protected CageRepository cageRepository;

    @Autowired
    protected KindRepository kindRepository;

    @Autowired
    protected AnimalRepository animalRepository;

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
}
