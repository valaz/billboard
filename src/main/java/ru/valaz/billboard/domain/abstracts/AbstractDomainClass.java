package ru.valaz.billboard.domain.abstracts;

import ru.valaz.billboard.domain.DomainObject;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractDomainClass implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private Date dateCreated;
    private Date lastUpdated;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }
}
