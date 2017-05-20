package ru.valaz.billboard.services.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.services.domain.NoteService;
import ru.valaz.billboard.services.repositories.NoteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
//@Profile("springdatajpa")
public class NoteServiceImpl implements NoteService {

    private Logger LOGGER = LoggerFactory.getLogger(BillboardServiceImpl.class);

    private NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> listAll() {
        List<Note> roles = new ArrayList<>();
        noteRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Note getById(Long id) {
        return noteRepository.findOne(id);
    }

    @Override
    public Note saveOrUpdate(Note domainObject) {
        return noteRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        Note note = noteRepository.findOne(id);
        note.getUser().getNotes().remove(note);
        note.getBillboard().getNotes().remove(note);
        noteRepository.delete(id);
        LOGGER.info("Note {} deleted by {}", id, note.getUser().getUsername());
    }
}