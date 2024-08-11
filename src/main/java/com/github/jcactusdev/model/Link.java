package com.github.jcactusdev.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public Link() {}

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url == null ? "" : url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return String.format("Link [name=%s, url=%s]", name, url);
    }

    @Override
    public int hashCode() {
        return 31 * ((name == null) ? 0 : name.hashCode())
                + 31 * ((url == null) ? 0 : url.hashCode());
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
        Link other = (Link) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.url, other.url);
    }
}
