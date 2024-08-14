package com.github.jcactusdev.model;

import com.github.jcactusdev.util.DateUtil;
import com.github.jcactusdev.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.jcactusdev.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    private Link homePage;

    private List<Position> positions = new ArrayList<>();

    public Organization() {}

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        return String.format("Organization [link=%s, positions=%s]", homePage.toString(), positions.toString());
    }

    @Override
    public int hashCode() {
        return 31 * ((homePage == null) ? 0 : homePage.hashCode());
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
        return Objects.equals(this.homePage, other.homePage);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        private static final long serialVersionUID = 1L;

        public static final Position EMPTY = new Position();

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {}

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            Objects.requireNonNull(endDate, "End date must not be null");
            Objects.requireNonNull(title, "Title date must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description == null ? "" : description;
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
            return String.format("Position [startDate=%s, endDate=%s, title=%s, description=%s]", startDate.toString(), endDate.toString(), title, description);
        }

        @Override
        public int hashCode() {
            return 31 * ((startDate == null) ? 0 : startDate.hashCode())
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
            Position other = (Position) otherObject;
            // Проверка хранимых значений в свойствах объекта
            return Objects.equals(startDate, other.startDate)
                    && Objects.equals(endDate, other.endDate)
                    && Objects.equals(title, other.title)
                    && Objects.equals(description, other.description);
        }
    }
}
