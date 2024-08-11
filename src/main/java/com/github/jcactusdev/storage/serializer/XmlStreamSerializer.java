package com.github.jcactusdev.storage.serializer;

import com.github.jcactusdev.model.*;
import com.github.jcactusdev.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        this.xmlParser = new XmlParser(Resume.class, Organization.class, Organization.Position.class, Link.class, OrganizationSection.class, TextSection.class, ListSection.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream stream) throws IOException {
        try (Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
