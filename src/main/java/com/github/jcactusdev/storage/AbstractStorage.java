package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.logging.Logger;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume object) {
        LOG.info(String.format("Save: %s", object.toString()));
        if (object == null) {
            throw new NullPointerException();
        }
        K key = getKey(object.getUUID());
        if (isExists(key)) {
            throw new RuntimeException("Key already exists");
        }
        doSave(object, key);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info(String.format("Get: %s", uuid));
        if (uuid == null) {
            throw new NullPointerException();
        }
        K key = getKey(uuid);
        if (!isExists(key)) {
            throw new RuntimeException("Key does not exist");
        }
        return doGet(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted()");
        List<Resume> result = doGetAll();
        Collections.sort(result);
        return result;
    }

    @Override
    public void update(Resume object) {
        LOG.info(String.format("Update: %s", object.toString()));
        if (object == null) {
            throw new NullPointerException();
        }
        K key = getKey(object.getUUID());
        if (!isExists(key)) {
            throw new RuntimeException("Key does not exist");
        }
        doUpdate(object, key);
    }

    @Override
    public void delete(String uuid) {
        LOG.info(String.format("Delete: %s", uuid));
        if (uuid == null) {
            throw new NullPointerException();
        }
        K key = getKey(uuid);
        if (!isExists(key)) {
            throw new RuntimeException("Key does not exist");
        }
        doDelete(key);
    }

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    public abstract String toString();

    protected abstract K getKey(String uuid);

    protected abstract void doSave(Resume object, K key);

    protected abstract Resume doGet(K key);

    protected abstract List<Resume> doGetAll();

    protected abstract void doUpdate(Resume object, K key);

    protected abstract void doDelete(K key);

    protected abstract boolean isExists(K key);
}
