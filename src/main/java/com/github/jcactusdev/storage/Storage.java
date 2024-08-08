package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

public interface Storage {
    void save(Resume object);

    Resume get(String uuid);

    Resume[] getAll();

    void update(Resume object);

    void delete(String uuid);

    void clear();

    int size();
}