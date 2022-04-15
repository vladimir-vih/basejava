package ru.javaops.basejava.webapp.model;

import java.util.List;

public class ExperienceSection implements Section<List<Experience>>{
    private List<Experience> body;

    ExperienceSection(){}

    ExperienceSection(List<Experience> list) {
        this.body = list;
    }

    @Override
    public List<Experience> getBody() {
        return body;
    }

    @Override
    public void updateBody(List<Experience> body) {
        this.body = body;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        for (Experience e : body) {
            sb.append("\n").append(e.toString());
        }
        return sb.toString();
    }
}
