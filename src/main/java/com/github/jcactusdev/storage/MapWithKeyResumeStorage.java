package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWithKeyResumeStorage extends AbstractStorage<Resume> {

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
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doSave(Resume object, Resume key) {
        storage.put(object.getUUID(), object);
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume object, Resume key) {
        storage.put(key.getUUID(), object);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.remove(key.getUUID());
    }

    @Override
    protected boolean isExists(Resume key) {
        return key != null;
    }
}
