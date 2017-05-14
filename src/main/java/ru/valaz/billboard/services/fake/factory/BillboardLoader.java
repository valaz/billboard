package ru.valaz.billboard.services.fake.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.services.domain.UserService;
import ru.valaz.billboard.services.repositories.BillboardRepository;
import ru.valaz.billboard.services.repositories.NoteRepository;

import java.time.LocalDateTime;

@Component
public class BillboardLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private BillboardRepository billboardRepository;
    private NoteRepository noteRepository;
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(BillboardLoader.class);

    @Autowired
    public BillboardLoader(BillboardRepository billboardRepository, NoteRepository noteRepository, UserService userService) {
        this.billboardRepository = billboardRepository;
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User user = userService.findByUsername("user");
        for (int i = 0; i < 5; i++) {
            Billboard billboard = new Billboard("Billboard " + i, "Billboard Description " + i);
            billboard.setLogoUrl("http://lorempixel.com/200/200/?random=" + ((i + 1) ^ 4));
            billboard.setUser(user);
            billboardRepository.save(billboard);
            user.addBillboard(billboard);

            log.info("Saved billboard: {} - id: {}", billboard.getName(), billboard.getId());

            for (int j = 0; j < 3; j++) {
                Note note = new Note(
                        "Note " + i * 1000 + j,
                        "Note Description " + i * 1000 + j,
                        LocalDateTime.now(),
                        billboard);
                note.setImageUrl("http://lorempixel.com/500/500/?random=" + ((i + j * 5 + 1) ^ 4));
                note.setUser(user);
                noteRepository.save(note);
                log.info("Saved note: {} - id: {}", note.getName(), note.getId());
                billboard.addNote(note);
                user.addNote(note);
            }
            billboardRepository.save(billboard);
            log.info("Updated billboard: {} - id: {}", billboard.getName(), billboard.getId());
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
