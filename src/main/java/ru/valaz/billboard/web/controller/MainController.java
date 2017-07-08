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
import ru.valaz.billboard.domain.dto.UserDto;
import ru.valaz.billboard.services.domain.BillboardService;
import ru.valaz.billboard.services.domain.UserService;
import ru.valaz.billboard.services.repositories.BillboardRepository;
import ru.valaz.billboard.services.repositories.NoteRepository;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BillboardRepository billboardRepository;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping("/billboards")
    public String billboards(Map<String, Object> model) {
        Iterable<Billboard> billboards = billboardService.listAll();
        model.put("billboards", billboards);
        return "billboards";
    }

    @RequestMapping("/billboard/show/{id}")
    public String getBillboard(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Billboard billboard = billboardRepository.findOne(id);
        model.put("billboard", billboard);
        model.put("notes", billboard.getNotes());
        return "billboard";
    }

    @RequestMapping("/note/show/{id}")
    public String getNote(Map<String, Object> model, @PathVariable(value = "id") Long id) {
        Note note = noteRepository.findOne(id);
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
