package ru.javaops.basejava.webapp.model;

import java.util.Objects;

public class Company {
    private final String name;
    private final Link url;

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
