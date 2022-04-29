package ru.javaops.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class SkillsSection implements Section<List<String>> {
    private final List<String> body;

    public SkillsSection(List<String> list) {
        Objects.requireNonNull(list, "Section can't be null");
        this.body = list;
    }

    @Override
    public List<String> getBody() {
        return body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (String s : body) {
            sb.append("\n").append("* ").append(s);
        }
        return sb.toString();
    }
}
