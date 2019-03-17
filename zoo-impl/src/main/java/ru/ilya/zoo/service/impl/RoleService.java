package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.repository.RoleRepository;

@Service
@Transactional
public class RoleService extends BaseService<Role> {

    private final RoleRepository roleRepository;

    public Role getByName(RoleName name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name.name(), Role.class));
    }

    public RoleService(JpaRepository<Role, Long> repository, RoleRepository roleRepository) {
        super(repository);
        this.roleRepository = roleRepository;
    }
}
