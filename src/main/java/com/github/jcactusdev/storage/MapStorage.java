package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

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
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Resume> entry : storage.entrySet()){
            stringBuilder.append(String.format("Key: %s, Resume: %s\n", entry.getKey(), entry.getValue()));
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
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
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume object, String key) {
        storage.put(key, object);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected boolean isExists(String key) {
        return storage.containsKey(key);
    }
}
