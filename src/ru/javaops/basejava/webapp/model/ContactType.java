package ru.javaops.basejava.webapp.model;

public enum ContactType {
    MOB_NUMBER("Phone number"),
    SKYPE("Skype"),
    MAIL("EMAIL"),
    LINKEDIN("LinkedIn profile"),
    GIT_HUB("GitHub profile"),
    STACK_OVERFLOW("StackOverflow profile"),
    HOMEPAGE("Home page");

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
