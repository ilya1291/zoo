package ru.ilya.zoo;

import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import ru.ilya.zoo.config.SecurityConfig;
import ru.ilya.zoo.model.*;
import ru.ilya.zoo.repository.*;

import java.io.File;
import java.util.Arrays;
import java.util.function.Supplier;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class IntegrationTest {

    @Value("${zoo.import.dir}")
    protected String importDirectory;
    @Value("${zoo.export.dir}")
    protected String exportDirectory;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private TransactionTemplate txTemplate;

    @Autowired
    protected MapperFacade mapperFacade;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

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
    public final void tearDown() throws Exception {
        clearDb();
        FileUtils.deleteDirectory(new File(importDirectory));
        FileUtils.deleteDirectory(new File(exportDirectory));
    }

    protected final void clearDb() {
        userRepository.deleteAll();
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

    protected User save(User user) {
        return doInTransaction(() -> userRepository.save(user));
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
