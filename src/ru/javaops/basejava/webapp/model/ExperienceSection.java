package ru.javaops.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection implements Section<List<Experience>> {
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
}
