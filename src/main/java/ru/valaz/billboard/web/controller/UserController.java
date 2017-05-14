package ru.valaz.billboard.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.services.domain.UserService;

import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/billboards")
    public String getUserBillboards(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Set<Billboard> billboards = userService.getBillboardsByUsername(username);
        model.put("billboards", billboards);
        return "user/billboards";
    }

    @RequestMapping("/user/notes")
    public String getUserNotes(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Set<Note> notes = userService.getNotesByUsername(username);
        model.put("notes", notes);
        return "user/notes";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
