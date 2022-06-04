package ru.javaops.basejava.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества", "CharacteristicSection"),
    OBJECTIVE("Позиция", "CharacteristicSection"),
    ACHIEVEMENT("Достижения", "SkillsSection"),
    QUALIFICATIONS("Квалификация", "SkillsSection"),
    EXPERIENCE("Опыт работы", "ExperienceSection"),
    EDUCATION("Образование", "ExperienceSection");

    private final String title;
    private final String classType;

    SectionType(String title, String classType) {
        this.title = title;
        this.classType = classType;
    }

    public String getTitle() {
        return title;
    }

    public String getClassType() {
        return classType;
    }
}
