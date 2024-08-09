package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWithKeyUUIDStorage extends AbstractStorage<String> {

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
    protected void doSave(Resume object, String uuid) {
        storage.put(uuid, object);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume object, String uuid) {
        storage.put(uuid, object);
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExists(String key) {
        return storage.containsKey(key);
    }
}
