package ru.javaops.basejava.webapp.model;

public interface Section<T> {
    T getBody();

    void updateBody(T section);

}
