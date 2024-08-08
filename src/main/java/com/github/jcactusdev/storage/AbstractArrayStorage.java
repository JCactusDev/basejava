package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume object, Integer key) {
        if (key >= 0) {
            throw new IllegalArgumentException();
        }
        if (size + 1 > STORAGE_LIMIT) {
            //System.out.println("Storage limit exceeded");
            throw new ArrayIndexOutOfBoundsException("Resume limit exceeded");
        }
        insertElement(object, key);
        size++;
    }

    @Override
    protected Resume doGet(String uuid) {
        Integer key = getKey(uuid);
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        return storage[key];
    }

    @Override
    protected void doUpdate(Resume object, Integer key) {
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        storage[key] = object;
    }

    @Override
    protected void doDelete(String uuid) {
        Integer key = getKey(uuid);
        if (key < 0) {
            throw new IllegalArgumentException();
        }
        removeElement(key);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertElement(Resume object, Integer key);

    protected abstract void removeElement(Integer key);
}
