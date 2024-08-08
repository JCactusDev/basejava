package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {

    @Override
    public void save(Resume object) {
        if (object == null) {
            throw new NullPointerException();
        }
        K key = getKey(object.getUUID());
        doSave(object, key);
    }

    @Override
    public Resume get(String uuid) {
        if (uuid == null) {
            throw new NullPointerException();
        }
        K key = getKey(uuid);
        return doGet(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = doGetAll();
        Collections.sort(result);
        return result;
    }

    @Override
    public void update(Resume object) {
        if (object == null) {
            throw new NullPointerException();
        }
        K key = getKey(object.getUUID());
        doUpdate(object, key);
    }

    @Override
    public void delete(String uuid) {
        if (uuid == null) {
            throw new NullPointerException();
        }
        K key = getKey(uuid);
        doDelete(key);
    }

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    public abstract String toString();

    protected abstract K getKey(String uuid);

    protected abstract void doSave(Resume object, K key);

    protected abstract Resume doGet(K key);

    protected abstract List<Resume> doGetAll();

    protected abstract void doUpdate(Resume object, K key);

    protected abstract void doDelete(K key);

}
