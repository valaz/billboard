package ru.valaz.billboard.domain;

import ru.valaz.billboard.domain.abstracts.AbstractDomainClass;

import javax.persistence.Entity;

@Entity
public class Role extends AbstractDomainClass {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
