package com.github.jcactusdev.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    public Resume() {
        uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return String.format("Resume [uuid=%s]", uuid);
    }

    @Override
    public int hashCode() {
        return 31 * ((uuid == null) ? 0 : uuid.hashCode());
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
        return Objects.equals(this.uuid, other.uuid);
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
        return cloneObject;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

}