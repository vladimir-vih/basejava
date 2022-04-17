package ru.javaops.basejava.webapp.model;

import java.util.List;

public class SkillsSection implements Section<List<String>>{
    private final List<String> body;

    SkillsSection(List<String> list) {
        this.body = list;
    }

    @Override
    public List<String> getBody() {
        return body;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        for (String s : body){
            sb.append("\n").append("* ").append(s);
        }
        return sb.toString();
    }
}
