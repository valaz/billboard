package ru.valaz.billboard.services.repositories;

import ru.valaz.billboard.domain.Billboard;
import org.springframework.data.repository.CrudRepository;

public interface BillboardRepository extends CrudRepository<Billboard, Long> {
}
