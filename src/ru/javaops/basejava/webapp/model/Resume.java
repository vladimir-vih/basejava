package ru.javaops.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{
    private String uuid;
    private final String fullName;
    private final EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final EnumMap<SectionType, Section> sections = new EnumMap<>(SectionType.class);


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContact(ContactType contactName) {
        return contacts.get(contactName);
    }

    public Section getSection(SectionType sectionName) {
        return sections.get(sectionName);
    }

    public void updateContact(ContactType contactName, String contact) {
        contacts.put(contactName, contact);
    }

    public void updateSection(SectionType sectionName, Section section) {
        sections.put(sectionName, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid)&&fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder contactsStringBuilder = new StringBuilder();
        for (Map.Entry<ContactType, String > entry : contacts.entrySet()) {
            contactsStringBuilder.append(entry.getKey().getTitle()).append(": ")
                    .append(entry.getValue()).append("\n");
        }
        final StringBuilder sectionsStringBuilder = new StringBuilder();
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            sectionsStringBuilder.append("\n========================================================\n")
                            .append(entry.getKey().getTitle()).append(": ")
                            .append(entry.getValue().toString())
                            .append("\n========================================================\n");
        }
        return "ФИО: " + fullName + "\n" +
                "Контакты:\n" + contactsStringBuilder +
                sectionsStringBuilder;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }
}
