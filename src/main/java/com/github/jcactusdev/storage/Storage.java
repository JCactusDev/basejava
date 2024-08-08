package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.List;

public interface Storage {
    void save(Resume object);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    void update(Resume object);

    void delete(String uuid);

    void clear();

    int size();
}