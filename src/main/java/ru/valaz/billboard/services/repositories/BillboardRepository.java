package ru.valaz.billboard.services.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.User;

import java.util.Set;

public interface BillboardRepository extends CrudRepository<Billboard, Long> {

    Set<Billboard> findAllBySubscribersContaining(User user);
}
