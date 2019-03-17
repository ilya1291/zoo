package ru.ilya.zoo.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.service.impl.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInitializer implements Initializer {

    private final UserService userService;

    @Override
    public void run() {
        initDefaultAdmin();
        initDefaultManager();
    }

    private void initDefaultAdmin() {
        User admin = new User()
                .setUsername("Admin1")
                .setPassword("123456")
                .setEmail("admin1@zoo.com");
        admin = userService.register(admin);
        userService.assignRole(admin.getId(), RoleName.ROLE_ADMIN);
    }

    private void initDefaultManager() {
        User manager = new User()
                .setUsername("Manager1")
                .setPassword("123456")
                .setEmail("manager1@zoo.com");
        manager = userService.register(manager);
        userService.assignRole(manager.getId(), RoleName.ROLE_MANAGER);
    }
}
