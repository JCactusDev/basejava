package com.github.jcactusdev.storage.serializer;

import com.github.jcactusdev.model.*;
import com.github.jcactusdev.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream stream) throws IOException {
        try (Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
