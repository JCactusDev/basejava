package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public String toString() {
        return storage.toString();
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUUID().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume object, Integer key) {
        if (key >= 0) {
            throw new IllegalArgumentException();
        }
        storage.add(object);
    }

    @Override
    protected Resume doGet(String uuid) {
        int key = getKey(uuid);
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        return storage.get(key);
    }

    @Override
    protected void doUpdate(Resume object, Integer key) {
        storage.set(key, object);
    }

    @Override
    protected void doDelete(String uuid) {
        Integer key = getKey(uuid);
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        storage.remove(key.intValue());
    }
}
