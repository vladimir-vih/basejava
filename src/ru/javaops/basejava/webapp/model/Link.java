package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private final String name;
    private final String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "Link name can't be null");
        Objects.requireNonNull(url, "Link URL can't be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return getName().equals(link.getName()) && getUrl().equals(link.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUrl());
    }

    @Override
    public String toString() {
        return "URL: " + url;
    }
}
