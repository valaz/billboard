package ru.valaz.billboard.services.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.services.domain.UserService;
import ru.valaz.billboard.services.repositories.UserRepository;
import ru.valaz.billboard.services.security.EncryptionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
//@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        if (domainObject.getPassword() != null) {
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<Billboard> getBillboardsByUsername(String username) {
        User user = findByUsername(username);
        return user.getBillboards();
    }

    @Override
    public Set<Note> getNotesByUsername(String username) {
        User user = findByUsername(username);
        return user.getNotes();
    }
}