package com.adridevelop.journalytic_backend.models.service;

import java.util.List;

public interface GeneralService<T> {

    public List<T> getAll();
    public T getOneById(Long id);
    public T save(T element);
    public T remove(T element);
}
