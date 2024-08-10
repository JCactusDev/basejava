package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "Directory must not be null");
        directory = Paths.get(dir);
        Objects.requireNonNull(streamSerializer, "StreamSerializer must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(String.format("%s - is not directory or is not writable", dir));
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    protected Path getKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(Resume object, Path key) {
        try {
            Files.createFile(key);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create Path " + key.getFileName().toString(), e);
        }
        doUpdate(object, key);
    }

    @Override
    protected Resume doGet(Path key) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(Paths.get(directory.toString(), key.getFileName().toString()))));
        } catch (IOException e) {
            throw new RuntimeException("Path read error", e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doUpdate(Resume object, Path key) {
        try {
            streamSerializer.doWrite(object, new BufferedOutputStream(Files.newOutputStream(key)));
        } catch (IOException e) {
            throw new RuntimeException("Path write error", e);
        }
    }

    @Override
    protected void doDelete(Path key) {
        try {
            Files.delete(key);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s - Path delete error", key.getFileName().toString()), e);
        }
    }

    @Override
    protected boolean isExists(Path key) {
        return Files.isRegularFile(key);
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
