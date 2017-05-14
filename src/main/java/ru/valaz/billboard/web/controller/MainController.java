package ru.valaz.billboard.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.services.repositories.BillboardRepository;
import ru.valaz.billboard.services.repositories.NoteRepository;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private BillboardRepository billboardRepository;
    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping("/billboards")
    public String billboards(Map<String, Object> model) {
        Iterable<Billboard> billboards = billboardRepository.findAll();
        model.put("billboards", billboards);
        return "billboards";
    }

    @RequestMapping("/billboard/show/{id}")
    public String getBillboard(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Billboard billboard = billboardRepository.findOne(id);
        model.put("billboard", billboard);
        return "billboard";
    }

    @RequestMapping("/notes/{id}")
    public String getNote(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Note note = noteRepository.findOne(id);
        model.put("note", note);
        return "note";
    }
}
