package ru.valaz.billboard.services.repositories;

import ru.valaz.billboard.domain.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
