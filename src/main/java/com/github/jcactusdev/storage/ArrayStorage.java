package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume object, Integer key) {
        storage[size] = object;
    }

    @Override
    protected void removeElement(Integer key) {
        storage[key] = storage[size - 1];
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(String.format("id: %d, Resume: %s", i, storage[i].toString()));
            if (i < size - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected Integer getKey(String uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUUID().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
