package ru.valaz.billboard.services.domain;

import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.services.CRUDService;

public interface BillboardService extends CRUDService<Billboard> {
    void addNewNoteToBillboard(Billboard billboard, Note note);
}