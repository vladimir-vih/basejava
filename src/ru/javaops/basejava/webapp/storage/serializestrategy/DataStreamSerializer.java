package ru.javaops.basejava.webapp.storage.serializestrategy;

import ru.javaops.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializeStrategy {
    @Override
    public void doWrite(BufferedOutputStream bos, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(),
                    (Map.Entry<ContactType, String> entry) -> writeContact(entry, dos), dos);
            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(),
                    (Map.Entry<SectionType, Section> entry) -> writeSection(entry, dos), dos);
        }
    }

    private <T> void writeWithException(Collection<T> collection,
                                        ThrowingObjectWriter<T> objectWriter, DataOutputStream dos) throws IOException {
        Objects.requireNonNull(collection);
        dos.writeInt(collection.size());
        for (T t : collection) {
            objectWriter.write(t);
        }
    }

    private void writeContact(Map.Entry<ContactType, String> contactEntry, DataOutputStream dos) throws IOException {
        dos.writeUTF(contactEntry.getKey().name());
        dos.writeUTF(contactEntry.getValue());
    }

    private void writeSection(Map.Entry<SectionType, Section> sectionEntry, DataOutputStream dos) throws IOException {
        final SectionType sectionType = sectionEntry.getKey();
        dos.writeUTF(sectionType.name());
        final Section section = sectionEntry.getValue();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                final Section<String> characteristicSection = (CharacteristicSection) section;
                dos.writeUTF(characteristicSection.getBody());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                final Section<List<String>> skillsSection = (SkillsSection) section;
                writeWithException(skillsSection.getBody(), dos::writeUTF, dos);
                break;
            case EXPERIENCE:
            case EDUCATION:
                final Section<List<Experience>> experienceSection = (ExperienceSection) section;
                writeWithException(experienceSection.getBody(), (Experience exp) -> writeExperience(exp, dos), dos);
                break;
        }
    }

    private void writeExperience(Experience experience, DataOutputStream dos) throws IOException {
        final Company company = experience.getCompany();
        dos.writeUTF(company.getName());
        final Link companyLink = company.getUrl();
        dos.writeBoolean(companyLink != null);
        if (companyLink != null) writeLink(companyLink, dos);
        dos.writeUTF(experience.getStartDate().toString());
        dos.writeUTF(experience.getEndDate().toString());
        dos.writeUTF(experience.getShortInfo());
        final String detailedInfo = experience.getDetailedInfo();
        dos.writeBoolean(detailedInfo != null);
        if (detailedInfo != null) dos.writeUTF(detailedInfo);
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());
    }

    @Override
    public Resume doRead(BufferedInputStream bis) throws IOException {
        try (DataInputStream dis = new DataInputStream(bis)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);

            Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            readWithException(() -> contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF()), dis);
            resume.setContacts(contacts);

            Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
            readWithException(() -> sectionReader(sections,dis), dis);
            resume.setSections(sections);
            return resume;
        }
    }

    private void readWithException(ThrowingObjectReader objectReader,
                                   DataInputStream dis) throws IOException {
        for (int amount = dis.readInt(); amount > 0; amount--) {
            objectReader.read();
        }
    }

    private void sectionReader(Map map, DataInputStream dis) throws IOException {
        final SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                final Section<String> section = new CharacteristicSection(dis.readUTF());
                map.put(sectionType, section);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                final List<String> skillsList = new ArrayList<>();
                readWithException(() -> skillsList.add(dis.readUTF()), dis);
                map.put(sectionType, new SkillsSection(skillsList));
                break;
            case EXPERIENCE:
            case EDUCATION:
                final List<Experience> experienceList = new ArrayList<>();
                readWithException(() -> experienceList.add(readExperience(dis)), dis);
                map.put(sectionType, new ExperienceSection(experienceList));
                break;
        }
    }

    private Experience readExperience(DataInputStream dis) throws IOException {
        String companyName = dis.readUTF();
        Link companyLink = dis.readBoolean() ? readLink(dis) : null;
        final Company company = new Company(companyName, companyLink);
        final LocalDate startDate = LocalDate.parse(dis.readUTF());
        final LocalDate endDate = LocalDate.parse(dis.readUTF());
        final String shortInfo = dis.readUTF();
        final String detailedInfo = dis.readBoolean() ? dis.readUTF() : null;
        return new Experience(company, startDate, endDate, shortInfo, detailedInfo);
    }

    private Link readLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), dis.readUTF());
    }
}

interface ThrowingObjectWriter<T> {
    void write(T t) throws IOException;
}

interface ThrowingObjectReader {
    void read() throws IOException;
}