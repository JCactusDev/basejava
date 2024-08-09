package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(String.format("%s - is not directory", directory.getAbsolutePath()));
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(String.format("%s - is not readable/writable", directory.getAbsolutePath()));
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new IllegalArgumentException(String.format("%s - directory read error", directory.getAbsolutePath()));
        }
        return list.length;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume object, File key) {
        try {
            key.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create file " + key.getAbsolutePath(), e);
        }
        doUpdate(object, key);
    }

    @Override
    protected Resume doGet(File key) {
        try {
            return doRead(key);
        } catch (IOException e) {
            throw new RuntimeException("File read error", e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("Directory read error");
        }
        List<Resume> result = new ArrayList<>(files.length);
        for (File file : files) {
            result.add(doGet(file));
        }
        return result;
    }

    @Override
    protected void doUpdate(Resume object, File key) {
        try {
            doWrite(object, key);
        } catch (IOException e) {
            throw new RuntimeException("File write error", e);
        }
    }

    @Override
    protected void doDelete(File key) {
        if (!key.delete()) {
            throw new IllegalArgumentException(String.format("%s - File delete error", directory.getAbsolutePath()));
        }
    }

    @Override
    protected boolean isExists(File key) {
        return key.exists();
    }

    protected abstract void doWrite(Resume object, File key) throws IOException;

    protected abstract Resume doRead(File key) throws IOException;

}
