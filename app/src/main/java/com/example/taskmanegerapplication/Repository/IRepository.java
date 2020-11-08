package com.example.taskmanegerapplication.Repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    List<E> getList();
    E get(UUID userId);
    void delete(E element);
    void insert(E element);
    void update(E element);
}
