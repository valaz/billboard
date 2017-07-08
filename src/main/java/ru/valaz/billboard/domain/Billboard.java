package ru.valaz.billboard.domain;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Billboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "billboard", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Note> notes;

    @ManyToOne
    private User user;

    private String logoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<User> subscribers;

    public Billboard() {
        // for Hibernate
    }

    public Billboard(String name, String description) {
        this.name = name;
        this.description = description;
        this.notes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean addNote(Note note) {
        notes.add(note);
        return true;
    }

    public boolean addSubscriber(User user) {
        subscribers.add(user);
        return true;
    }

    public boolean removeSubscriber(User user) {
        subscribers.remove(user);
        return true;
    }
}
