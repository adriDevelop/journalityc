package com.adridevelop.journalytic_backend.models.service;

import java.util.ArrayList;

public interface GeneralService<T> {

    public ArrayList<T> getAll();
    public T getOneById(Long id);
    public T save(T element);
    public T remove(T element);
}
