package com.github.jcactusdev.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private String fullName;

    public Resume() {
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

    public String getUUID() {
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

    @Override
    public String toString() {
        return String.format("Resume [uuid=%s, fullName=%s]", uuid, fullName);
    }

    @Override
    public int hashCode() {
        return 11 * ((uuid == null) ? 0 : uuid.hashCode())
                + 20 * ((fullName == null) ? 0 : fullName.hashCode());
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
        return Objects.equals(this.uuid, other.uuid)
                && Objects.equals(this.fullName, other.fullName);
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
        return cloneObject;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}