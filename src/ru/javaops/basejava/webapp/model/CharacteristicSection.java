package ru.javaops.basejava.webapp.model;

public class CharacteristicSection implements Section<String>{
    private final String body;

    CharacteristicSection(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString(){
        return body;
    }
}
