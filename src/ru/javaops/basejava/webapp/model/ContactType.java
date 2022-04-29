package ru.javaops.basejava.webapp.model;

public enum ContactType {
    MOB_NUMBER("Телефон"),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GIT_HUB("Профиль GitHub"),
    STACK_OVERFLOW("Профиль StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String s) {
        setTitle(s);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
