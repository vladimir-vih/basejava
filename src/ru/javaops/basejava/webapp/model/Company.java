package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private String name;
    private Link url;

    public Company() {
    }

    public Company(String name) {
        this(name, null);
    }

    public Company(String name, Link url) {
        Objects.requireNonNull(name, "Company name can't be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Link getUrl() {
        return url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (url != null) sb.append("\n").append(url);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return getName().equals(company.getName()) && Objects.equals(getUrl(), company.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
