package ru.valaz.billboard.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.domain.dto.UserDto;
import ru.valaz.billboard.services.domain.BillboardService;
import ru.valaz.billboard.services.domain.NoteService;
import ru.valaz.billboard.services.domain.UserService;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private NoteService noteService;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping("/billboards")
    public String billboards(Map<String, Object> model) {
        Iterable<Billboard> billboards = billboardService.getTopBillboards(3);
        model.put("billboards", billboards);
        return "billboards";
    }

    @RequestMapping("/billboard/show/{id}")
    public String getBillboard(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Billboard billboard = billboardService.getById(id);
        if (billboard == null) {
            return "error/404";
        }
        model.put("billboard", billboard);
        model.put("billboard_subscribers", billboard.getSubscribers()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toSet()));
        model.put("notes", billboard.getNotes());
        return "billboard";
    }

    @RequestMapping("/note/show/{id}")
    public String getNote(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Note note = noteService.getById(id);
        model.put("note", note);
        return "note";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Map<String, Object> model) {
        UserDto user = new UserDto();
        model.put("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto,
                                      BindingResult result, WebRequest request, Errors errors) {
        if (!result.hasErrors()) {
            userService.createUserAccount(accountDto, result);
        }
        return "redirect:login";
    }
}
