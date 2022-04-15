package ru.javaops.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class SkillsSection implements Section<List<String>>{
    private List<String> body = new ArrayList<>();

    SkillsSection(){}

    SkillsSection(List<String> list) {
        this.body = list;
    }

    @Override
    public List<String> getBody() {
        return body;
    }

    @Override
    public void updateBody(List<String> body) {
        this.body = body;
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
