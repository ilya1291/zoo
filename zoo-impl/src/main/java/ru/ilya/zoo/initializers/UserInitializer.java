package ru.ilya.zoo.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.service.impl.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInitializer implements Initializer {

    @Value("${default-users.admin.name}")
    private String adminName;
    @Value("${default-users.admin.password}")
    private String adminPassword;
    @Value("${default-users.admin.email}")
    private String adminEmail;

    @Value("${default-users.manager.name}")
    private String managerName;
    @Value("${default-users.manager.password}")
    private String managerPassword;
    @Value("${default-users.manager.email}")
    private String managerEmail;

    private final UserService userService;

    @Override
    public void run() {
        initDefaultAdmin();
        initDefaultManager();
    }

    private void initDefaultAdmin() {
        User admin = new User()
                .setUsername(adminName)
                .setPassword(adminPassword)
                .setEmail(adminEmail);
        admin = userService.register(admin);
        userService.assignRole(admin.getId(), RoleName.ROLE_ADMIN);
    }

    private void initDefaultManager() {
        User manager = new User()
                .setUsername(managerName)
                .setPassword(managerPassword)
                .setEmail(managerPassword);
        manager = userService.register(manager);
        userService.assignRole(manager.getId(), RoleName.ROLE_MANAGER);
    }
}
