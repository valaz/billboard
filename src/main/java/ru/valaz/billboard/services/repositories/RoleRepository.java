package ru.valaz.billboard.services.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.valaz.billboard.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
