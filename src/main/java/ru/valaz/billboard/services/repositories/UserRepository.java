package ru.valaz.billboard.services.repositories;

import ru.valaz.billboard.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);
}
