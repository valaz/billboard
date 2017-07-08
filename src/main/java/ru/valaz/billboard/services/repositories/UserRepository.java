package ru.valaz.billboard.services.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.valaz.billboard.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
