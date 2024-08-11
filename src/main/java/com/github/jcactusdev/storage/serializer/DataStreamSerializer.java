package com.github.jcactusdev.storage.serializer;

import com.github.jcactusdev.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume object, OutputStream key) throws IOException {
        try (DataOutputStream out = new DataOutputStream(key)) {
            out.writeUTF(object.getUUID());
            out.writeUTF(object.getFullName());
            Map<ContactType, String> contacts = object.getContacts();
            writeItems(out, contacts.entrySet(), entry -> {
                out.writeUTF(entry.getKey().name());
                out.writeUTF(entry.getValue());
            });
            //
            Map<SectionType, Section> sections = object.getSections();
            writeItems(out, sections.entrySet(), entry -> {
                SectionType type = entry.getKey();
                out.writeUTF(type.name());
                Section section = entry.getValue();
                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        out.writeUTF(((TextSection) section).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        writeItems(out, ((ListSection) section).getItems(), out::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        writeItems(out, ((OrganizationSection) section).getOrganizations(), org -> {
                            out.writeUTF(org.getHomePage().getName());
                            out.writeUTF(org.getHomePage().getUrl());
                            writeItems(out, org.getPositions(), position -> {
                                writeLocaDate(out, position.getStartDate());
                                writeLocaDate(out, position.getEndDate());
                                out.writeUTF(position.getTitle());
                                out.writeUTF(position.getDescription());
                            });
                        });
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resume doRead(InputStream key) throws IOException {
        try (DataInputStream in = new DataInputStream(key)) {
            Resume resume = new Resume(in.readUTF(), in.readUTF());
            readItems(in, () -> resume.addContact(ContactType.valueOf(in.readUTF()), in.readUTF()));
            readItems(in, () -> {
                SectionType type = SectionType.valueOf(in.readUTF());
                resume.addSection(type, readSection(in, type));
            });
            return resume;
        }
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }

    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private <T> void writeItems(DataOutputStream stream, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        stream.writeInt(collection.size());
        for(T item : collection) {
            writer.write(item);
        }
    }

    private void readItems(DataInputStream stream, ElementProcessor processor) throws IOException {
        int size = stream.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private void writeLocaDate(DataOutputStream stream, LocalDate localDate) throws IOException {
        stream.writeInt(localDate.getYear());
        stream.writeInt(localDate.getMonthValue());
    }

    private LocalDate readLocaDate(DataInputStream stream) throws IOException {
        return LocalDate.of(stream.readInt(), stream.readInt(), 1);
    }

    private Section readSection(DataInputStream stream, SectionType type) throws IOException {
        return switch (type) {
            case OBJECTIVE, PERSONAL -> new TextSection(stream.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readList(stream, stream::readUTF));
            case EXPERIENCE, EDUCATION -> new OrganizationSection(readList(stream, ()-> new Organization(
                    new Link(stream.readUTF(), stream.readUTF()),
                    readList(stream, () -> new Organization.Position(
                            readLocaDate(stream),
                            readLocaDate(stream),
                            stream.readUTF(),
                            stream.readUTF()
                    ))
            )));
            default -> throw new IllegalArgumentException();
        };
    }

    private <T> List<T> readList(DataInputStream stream, ElementReader<T> reader) throws IOException {
        int size = stream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

}
