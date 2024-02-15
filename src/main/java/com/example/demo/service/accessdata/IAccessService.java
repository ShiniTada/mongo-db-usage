package com.example.demo.service.accessdata;

import java.util.List;

public interface IAccessService<D> {

    D getById(String id);

    List<D> getAll();

    D create(D d);

    D update(D d);

    void delete(String id);
}
