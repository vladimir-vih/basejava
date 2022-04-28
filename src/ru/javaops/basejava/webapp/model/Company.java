package ru.javaops.basejava.webapp.model;

import java.util.Objects;

public class Company {
    private final String companyName;
    private final Link companyUrl;

    Company(String name, Link url){
        Objects.requireNonNull(name, "Company name can't be null");
        this.companyName = name;
        this.companyUrl = url;
    }

    Company(String name) {
        this(name, null);
    }

    public String getCompanyName() {
        return companyName;
    }

    public Link getCompanyUrl() {
        return companyUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(companyName);
        if(companyUrl != null) sb.append("\n").append(companyUrl);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return getCompanyName().equals(company.getCompanyName()) && Objects.equals(getCompanyUrl(), company.getCompanyUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName());
    }
}
