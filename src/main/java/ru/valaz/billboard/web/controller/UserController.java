package ru.valaz.billboard.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.services.domain.BillboardService;
import ru.valaz.billboard.services.domain.NoteService;
import ru.valaz.billboard.services.domain.UserService;

import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private NoteService noteService;

    @RequestMapping("/user/billboards")
    public String getUserBillboards(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Set<Billboard> billboards = userService.getBillboardsByUsername(username);
        model.put("billboards", billboards);
        return "user/billboards";
    }

    @RequestMapping("/user/billboard/new")
    public String newBillboard(Model model) {
        model.addAttribute("billboard", new Billboard());
        return "user/billboardform";
    }

    @RequestMapping(value = "/user/billboard/add", method = RequestMethod.POST)
    public String saveBillboard(Billboard billboard) {
        billboard.setId(null);
        Billboard savedBillboard = billboardService.addBillboard(billboard);
        return "redirect:/billboard/show/" + savedBillboard.getId();
    }

    @RequestMapping(value = "/user/billboard/delete/{id}", method = RequestMethod.POST)
    public String deleteBillboard(@PathVariable Long id) {
        billboardService.delete(id);
        return "redirect:/user/billboards";
    }

    @RequestMapping("/billboard/{id}/new")
    public String newNote(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Billboard billboard = billboardService.getById(id);
        model.put("billboard", billboard);
        model.put("note", new Note());
        return "user/noteform";
    }

    @RequestMapping(value = "/billboard/{id}/new", method = RequestMethod.POST)
    public String addNote(Map<String, Object> model, @PathVariable(value = "id") Long id, @ModelAttribute(value = "note") Note note) {
        Billboard billboard = billboardService.getById(id);
        model.put("billboard", billboard);
        billboardService.addNewNoteToBillboard(billboard, note);
        return "redirect:/billboard/show/" + id;
    }

    @RequestMapping(value = "/user/note/delete/{id}", method = RequestMethod.POST)
    public String deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/user/notes";
    }

    @RequestMapping("/user/notes")
    public String getUserNotes(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Set<Note> notes = userService.getNotesByUsername(username);
        model.put("notes", notes);
        return "user/notes";
    }
}
