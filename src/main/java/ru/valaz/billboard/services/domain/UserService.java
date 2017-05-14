package ru.valaz.billboard.services.domain;

import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.services.CRUDService;

import java.util.Set;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

    Set<Billboard> getBillboardsByUsername(String username);

    Set<Note> getNotesByUsername(String username);

}