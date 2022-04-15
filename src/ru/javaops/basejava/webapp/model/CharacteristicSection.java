package ru.javaops.basejava.webapp.model;

public class CharacteristicSection implements Section<String>{
    private String body;

    CharacteristicSection() {}

    CharacteristicSection(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void updateBody(String s) {
        body = s;
    }

    @Override
    public String toString(){
        return body;
    }
}
