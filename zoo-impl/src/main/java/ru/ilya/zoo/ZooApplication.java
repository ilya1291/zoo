package ru.ilya.zoo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ilya.zoo.model.Animal;

@Slf4j
@SpringBootApplication
@EntityScan(basePackageClasses = {Animal.class})
@EnableJpaRepositories(basePackages = "ru.ilya.zoo.repository")
public class ZooApplication {

    public static void main(String[] args) {
        log.info("Version: {}", ZooApplication.class.getPackage().getImplementationVersion());
        ConfigurableApplicationContext context = SpringApplication.run(ZooApplication.class, args);
    }
}
