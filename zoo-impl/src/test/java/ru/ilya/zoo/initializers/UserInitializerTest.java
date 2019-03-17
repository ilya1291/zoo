package ru.ilya.zoo.initializers;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.ilya.zoo.IntegrationTest;

import java.util.List;

public class UserInitializerTest extends IntegrationTest {

    @Value("${default-users.admin.email}")
    private String adminEmail;
    @Value("${default-users.manager.email}")
    private String managerEmail;

    @Autowired
    private List<Initializer> initializers;

    @Test
    public void testInitializationUsers() {
        initializers.forEach(Runnable::run);

        userRepository.existsByEmail(adminEmail);
        userRepository.existsByEmail(managerEmail);
    }
}