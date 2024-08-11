package com.github.jcactusdev.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public OrganizationSection() {}

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "Organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public int hashCode() {
        return 31 * ((organizations == null) ? 0 : organizations.hashCode());
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
        OrganizationSection other = (OrganizationSection) otherObject;
        // Проверка хранимых значений в свойствах объекта
        return Objects.equals(this.organizations, other.organizations);
    }
}
