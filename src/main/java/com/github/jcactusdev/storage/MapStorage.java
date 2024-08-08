package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
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
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume object, String key) {
        storage.put(key, object);
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doUpdate(Resume object, String key) {
        storage.put(key, object);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }
}
