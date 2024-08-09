package com.github.jcactusdev.model;

import java.util.Objects;

public class TextSection extends Section {
    private final String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public int hashCode() {
        return 31 * ((content == null) ? 0 : content.hashCode());
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
        TextSection other = (TextSection) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(this.content, other.content);
    }

}
