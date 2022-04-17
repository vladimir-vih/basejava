package ru.javaops.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{
    private String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts;
    private final Map<SectionType, Section<?>> sections;

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts, Map<SectionType, Section<?>> sections) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
        this.sections = sections;
    }

    public Resume(String uuid, String fullName) {
        this(uuid, fullName, new EnumMap<>(ContactType.class), new EnumMap<>(SectionType.class));
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
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

    public Section<?> getSection(SectionType sectionName) {
        return sections.get(sectionName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return getUuid().equals(resume.getUuid())
                && getFullName().equals(resume.getFullName())
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), contacts, sections);
    }

    @Override
    public String toString() {
        final StringBuilder contactsStringBuilder = new StringBuilder();
        for (Map.Entry<ContactType, String > entry : contacts.entrySet()) {
            contactsStringBuilder.append(entry.getKey().getTitle()).append(": ")
                    .append(entry.getValue()).append("\n");
        }
        final StringBuilder sectionsStringBuilder = new StringBuilder();
        for (Map.Entry<SectionType, Section<?>> entry : sections.entrySet()) {
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
