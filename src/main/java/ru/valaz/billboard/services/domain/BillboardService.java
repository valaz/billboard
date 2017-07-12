package ru.valaz.billboard.services.domain;

import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.domain.dto.BillboardDto;
import ru.valaz.billboard.domain.dto.NoteDto;
import ru.valaz.billboard.services.CRUDService;

import java.util.Set;

public interface BillboardService extends CRUDService<Billboard> {

    Billboard addBillboard(Billboard billboard);

    Billboard addBillboard(BillboardDto billboard);

    void addNewNoteToBillboard(Billboard billboard, Note note);

    void addNewNoteToBillboard(Billboard billboard, NoteDto note);

    void addSubscriber(Long id);

    void removeSubscriber(Long id);

    Set<Billboard> getBillboardsByUser(User user);

    Iterable<Billboard> getTopBillboards(int count);
}