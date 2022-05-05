package ru.javaops.basejava.webapp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ExperienceSection implements Section<List<Experience>>, Serializable {
    private final List<Experience> body;

    public ExperienceSection(List<Experience> list) {
        Objects.requireNonNull(list, "Section can't be null");
        this.body = list;
    }

    @Override
    public List<Experience> getBody() {
        return body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Experience e : body) {
            sb.append("\n").append(e.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return getBody().equals(that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody());
    }
}
