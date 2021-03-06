package ru.valaz.billboard.services.domain.impl;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.domain.Note;
import ru.valaz.billboard.domain.Role;
import ru.valaz.billboard.domain.User;
import ru.valaz.billboard.domain.dto.UserDto;
import ru.valaz.billboard.services.domain.RoleService;
import ru.valaz.billboard.services.domain.UserService;
import ru.valaz.billboard.services.repositories.BillboardRepository;
import ru.valaz.billboard.services.repositories.UserRepository;
import ru.valaz.billboard.services.security.EncryptionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private EncryptionService encryptionService;

    private RoleService roleService;

    private ModelMapper modelMapper;

    private BillboardRepository billboardRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EncryptionService encryptionService, RoleService roleService, ModelMapper modelMapper, BillboardRepository billboardRepository) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.billboardRepository = billboardRepository;
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

    @Transactional
    @Override
    public void saveOrUpdate(String authName, UserDto accountDto) {
        User user = userRepository.findByUsername(authName);
        user.setEmail(accountDto.getEmail());
        user.setName(accountDto.getName());
        if (StringUtils.isNotBlank(accountDto.getPassword())) {
            user.setPassword(accountDto.getPassword());
        }
        saveOrUpdate(user);
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

    @Override
    public User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        try {
            registered = registerNewUserAccount(accountDto);
        } catch (Exception e) {
            return null;
        }
        return registered;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        User user = modelMapper.map(accountDto, User.class);
        Role role = roleService.getRole("USER");
        user.addRole(role);
        saveOrUpdate(user);
        return user;
    }

    @Override
    public Set<Billboard> getSubscribeBillboardsByUsername(String username) {
        User user = findByUsername(username);
        return billboardRepository.findAllBySubscribersContaining(user);
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    private boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}