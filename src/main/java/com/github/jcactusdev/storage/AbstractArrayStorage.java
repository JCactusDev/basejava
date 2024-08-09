package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    public static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

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
        if (size + 1 > STORAGE_LIMIT) {
            throw new ArrayIndexOutOfBoundsException("Resume limit exceeded");
        }
        insertElement(object, key);
        size++;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void doUpdate(Resume object, Integer key) {
        storage[key] = object;
    }

    @Override
    protected void doDelete(Integer key) {
        removeElement(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExists(Integer key) {
        return key >= 0;
    }

    protected abstract void insertElement(Resume object, Integer key);

    protected abstract void removeElement(Integer key);
}
