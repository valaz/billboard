package ru.valaz.billboard.services.domain.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.domain.dto.BillboardDto;
import ru.valaz.billboard.domain.dto.NoteDto;
import ru.valaz.billboard.services.domain.BillboardService;
import ru.valaz.billboard.services.domain.NoteService;
import ru.valaz.billboard.services.domain.UserService;
import ru.valaz.billboard.services.repositories.BillboardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillboardServiceImpl implements BillboardService {

    private Logger logger = LoggerFactory.getLogger(BillboardServiceImpl.class);

    private BillboardRepository billboardRepository;

    private NoteService noteService;

    private UserService userService;

    private ModelMapper modelMapper;

    @Autowired
    public BillboardServiceImpl(BillboardRepository billboardRepository, NoteService noteService, UserService userService, ModelMapper modelMapper) {
        this.billboardRepository = billboardRepository;
        this.noteService = noteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Billboard> listAll() {
        List<Billboard> billboards = new ArrayList<>();
        billboardRepository.findAll().forEach(billboards::add);
        return billboards;
    }

    @Override
    public Billboard getById(Long id) {
        return billboardRepository.findOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Billboard saveOrUpdate(Billboard domainObject) {
        return billboardRepository.save(domainObject);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Billboard addBillboard(Billboard billboard) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new UsernameNotFoundException("need auth");
        }
        billboard.setUser(user);
        if (billboard.getLogoUrl() == null || billboard.getLogoUrl().isEmpty()) {
            billboard.setLogoUrl("//placehold.it/450X300/DD3333/EE3333");
        }
        Billboard savedBillboard = billboardRepository.save(billboard);
        user.addBillboard(savedBillboard);
        userService.saveOrUpdate(user);
        return savedBillboard;
    }

    @Override
    public Billboard addBillboard(BillboardDto billboard) {
        return addBillboard(modelMapper.map(billboard, Billboard.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Billboard billboard = billboardRepository.findOne(id);
        billboard.getUser().getBillboards().remove(billboard);
        billboardRepository.delete(id);
        logger.info("Billboard {} deleted by {}", id, billboard.getUser().getUsername());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewNoteToBillboard(Billboard billboard, Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByUsername(currentPrincipalName);
        if (user == null) {
            throw new UsernameNotFoundException("need auth");
        }
        note.setUser(user);
        note.setId(null);
        if (note.getImageUrl() == null || note.getImageUrl().isEmpty()) {
            note.setImageUrl("http://lorempixel.com/500/500/?random=333");
        }
        note.setBillboard(billboard);
        Note savedNote = noteService.saveOrUpdate(note);
        billboard.addNote(savedNote);
        user.addNote(savedNote);
        saveOrUpdate(billboard);
        userService.saveOrUpdate(user);
    }

    @Override
    public void addNewNoteToBillboard(Billboard billboard, NoteDto note) {
        addNewNoteToBillboard(billboard, modelMapper.map(note, Note.class));
    }
}
