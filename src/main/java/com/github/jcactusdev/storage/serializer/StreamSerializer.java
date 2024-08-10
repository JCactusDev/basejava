package com.github.jcactusdev.storage.serializer;

import com.github.jcactusdev.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void doWrite(Resume resume, OutputStream stream) throws IOException;

    Resume doRead(InputStream stream) throws IOException;
}
