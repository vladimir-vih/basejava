package ru.javaops.basejava.webapp.model;

public enum SectionType {
    PERSONAL("Personal information", "string"),
    OBJECTIVE("Looking for position", "string"),
    ACHIEVEMENT("Achievements", "list"),
    QUALIFICATIONS("Qualifications", "list"),
    EXPERIENCE("Work experience", "experience"),
    EDUCATION("Education", "experience");

    private final String title;
    private final String displayType;

    SectionType(String title, String displayType) {
        this.title = title;
        this.displayType = displayType;
    }

    public String getTitle() {
        return title;
    }

    public String getDisplayType() {
        return displayType;
    }
}
