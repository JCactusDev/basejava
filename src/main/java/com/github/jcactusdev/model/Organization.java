package com.github.jcactusdev.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "Start date must not be null");
        Objects.requireNonNull(endDate, "End date must not be null");
        Objects.requireNonNull(title, "Title date must not be null");
        this.homePage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getHomePage() {
        return homePage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Organization [link=%s, startDate=%s, endDate=%s, title=%s, description=%s]", homePage.toString(), startDate.toString(), endDate.toString(), title, description);
    }

    @Override
    public int hashCode() {
        return 31 * ((homePage == null) ? 0 : homePage.hashCode())
                + 31 * ((startDate == null) ? 0 : startDate.hashCode())
                + 31 * ((endDate == null) ? 0 : endDate.hashCode())
                + 31 * ((title == null) ? 0 : title.hashCode())
                + 31 * ((description == null) ? 0 : description.hashCode());
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
        Organization other = (Organization) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(this.homePage, other.homePage)
                && Objects.equals(this.startDate, other.startDate)
                && Objects.equals(this.endDate, other.endDate)
                && Objects.equals(this.title, other.title)
                && Objects.equals(this.description, other.description);
    }
}
