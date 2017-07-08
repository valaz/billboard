package ru.valaz.billboard.domain.dto;

import java.util.Set;

public class BillboardDto {

    private Long id;

    private String name;

    private String description;

    private UserDto user;

    private Set<NoteDto> notes;

    private String logoUrl;

    private Set<UserDto> subscribers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteDto> notes) {
        this.notes = notes;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Set<UserDto> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<UserDto> subscribers) {
        this.subscribers = subscribers;
    }
}
