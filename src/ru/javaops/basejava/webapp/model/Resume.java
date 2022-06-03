package ru.javaops.basejava.webapp.model;

import ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters.SectionXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    @XmlAttribute
    private String uuid;
    @XmlAttribute
    private String fullName;
    private Map<ContactType, String> contacts;
    @XmlJavaTypeAdapter(SectionXmlAdapter.class)
    private Map<SectionType, Section> sections;


    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this(uuid, fullName, new EnumMap<>(ContactType.class), new EnumMap<>(SectionType.class));
    }

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = contacts;
        this.sections = sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getContact(ContactType contactName) {
        return contacts.get(contactName);
    }

    public Section<?> getSection(SectionType sectionName) {
        return sections.get(sectionName);
    }

    public void addContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return getFullName().equals(resume.getFullName())
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections)
                && getUuid().equals(resume.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), contacts, sections);
    }

    @Override
    public String toString() {
        final StringBuilder contactsStringBuilder = new StringBuilder();
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
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
