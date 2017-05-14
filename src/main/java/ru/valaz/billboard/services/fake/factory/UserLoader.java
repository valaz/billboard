package ru.valaz.billboard.services.fake.factory;

import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.services.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserLoader.class);

    @Autowired
    public UserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        User su = new User();
        su.setName("su");
        su.setPassword("1q2w3e4r");
        userRepository.save(su);

        log.info("Saved su - id: {}", su.getId());

        User sa = new User();
        sa.setName("sa");
        sa.setPassword("1q2w3e4r");
        userRepository.save(sa);

        log.info("Saved sa - id: {}", sa.getId());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
