package ru.javaops.basejava.webapp.model;

import java.util.Objects;

public class CharacteristicSection implements Section<String>{
    private final String body;

    CharacteristicSection(String body) {
        Objects.requireNonNull(body, "Section can't be null");
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
