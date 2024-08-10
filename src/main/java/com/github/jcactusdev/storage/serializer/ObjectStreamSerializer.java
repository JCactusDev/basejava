package com.github.jcactusdev.storage.serializer;

import com.github.jcactusdev.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume object, OutputStream key) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(key)) {
            out.writeObject(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume doRead(InputStream key) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(key)) {
            return (Resume) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
