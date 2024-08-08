package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

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
        for (int i = 0; i < storage.size(); i++) {
            stringBuilder.append(String.format("id: %d, Resume: %s", i, storage.get(i).toString()));
            if (i < storage.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
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
    protected Resume doGet(Integer key) {
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        return storage.get(key);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage);
    }

    @Override
    protected void doUpdate(Resume object, Integer key) {
        storage.set(key, object);
    }

    @Override
    protected void doDelete(Integer key) {
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        storage.remove(key.intValue());
    }
}
