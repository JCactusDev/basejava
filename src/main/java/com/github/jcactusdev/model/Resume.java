package com.github.jcactusdev.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    // Unique identifier
    private String uuid;
    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        this(UUID.randomUUID().toString(), "");
    }
    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }
    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }
    public String getContact(ContactType type) {
        return contacts.get(type);
    }
    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }
    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }
    public Section getSection(SectionType type) {
        return sections.get(type);
    }
    public void setSection(SectionType type, Section value) {
        sections.put(type, value);
    }
    public void addSection(SectionType type, Section value) {
        sections.put(type, value);
    }

    @Override
    public String toString() {
        return String.format("Resume [uuid=%s, fullName=%s, contacts=%s, sections=%s]", uuid, fullName, contacts, sections);
    }

    @Override
    public int hashCode() {
        return 31 * ((uuid == null) ? 0 : uuid.hashCode())
                + 31 * ((fullName == null) ? 0 : fullName.hashCode())
                + 31 * ((contacts == null) ? 0 : contacts.hashCode())
                + 31 * ((sections == null) ? 0 : sections.hashCode());
    }

    @Override
    public boolean equals(Object otherObject) {
        // Проверка объектов на идентичность
        if (this == otherObject) {
            return true;
        }
        // Проверка явного параметра == null
        if (otherObject == null) {
            return false;
        }
        // Проверка совпадения классов
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }
        // Приведение otherObject к типу текущего класа
        Resume other = (Resume) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(uuid, other.uuid)
                && Objects.equals(fullName, other.fullName)
                && Objects.equals(contacts, other.contacts)
                && Objects.equals(sections, other.sections);
    }

    @Override
    public Resume clone() {
        Resume cloneObject;
        try {
            cloneObject = (Resume) super.clone();
        } catch (CloneNotSupportedException e) {
            cloneObject = new Resume();
        }
        cloneObject.uuid = uuid;
        cloneObject.fullName = fullName;
        cloneObject.contacts.putAll(contacts);
        cloneObject.sections.putAll(sections);
        return cloneObject;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}