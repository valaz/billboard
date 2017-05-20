package ru.valaz.billboard.services;

import java.util.List;

public interface CRUDService<T> {

    List<T> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}