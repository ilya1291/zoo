package ru.ilya.zoo.repository;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;

public class RoleRepositoryTest extends IntegrationTest {

    @Test
    public void shouldFindByName() {
        roleRepository.findByName(RoleName.ROLE_ADMIN).get();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveRoleWithDuplicatedName() {
        roleRepository.save(new Role()
                .setId(100L)
                .setName(RoleName.ROLE_MANAGER)
        );
    }
}