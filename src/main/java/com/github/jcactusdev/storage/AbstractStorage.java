package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

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
        return doGet(uuid);
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
        doDelete(uuid);
    }

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    public abstract String toString();

    protected abstract K getKey(String uuid);

    protected abstract void doSave(Resume object, K key);

    protected abstract Resume doGet(String uuid);

    protected abstract void doUpdate(Resume object, K key);

    protected abstract void doDelete(String uuid);

}
