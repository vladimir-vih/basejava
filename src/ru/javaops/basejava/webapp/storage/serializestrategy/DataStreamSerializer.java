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
            writeWithException(contacts.entrySet(), this::writeContact, dos);
            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(), this::writeSection, dos);
        }
    }

    private <T> void writeWithException(Collection<T> collection,
                                        ThrowingObjectWriter<T> objectWriter, DataOutputStream dos) throws IOException {
        Objects.requireNonNull(collection);
        dos.writeInt(collection.size());
        for (T t : collection) {
            objectWriter.accept(t, dos);
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
                writeWithException(skillsSection.getBody(), (String s, DataOutputStream d) -> d.writeUTF(s), dos);
                break;
            case EXPERIENCE:
            case EDUCATION:
                final Section<List<Experience>> experienceSection = (ExperienceSection) section;
                writeWithException(experienceSection.getBody(), this::writeExperience, dos);
                break;
        }
    }

    private void writeExperience(Experience experience, DataOutputStream dos) throws IOException {
        final Company company = experience.getCompany();
        dos.writeUTF(company.getName());
        final Link companyLink = company.getUrl();
        if (writeOptionalFieldFlag(companyLink, dos)) writeLink(companyLink, dos);
        dos.writeUTF(experience.getStartDate().toString());
        dos.writeUTF(experience.getEndDate().toString());
        dos.writeUTF(experience.getShortInfo());
        final String detailedInfo = experience.getDetailedInfo();
        if (writeOptionalFieldFlag(detailedInfo, dos)) dos.writeUTF(detailedInfo);
    }

    private void writeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());
    }

    private <T> boolean writeOptionalFieldFlag(T object, DataOutputStream dos) throws IOException {
        if (object != null) {
            dos.writeBoolean(true);
            return true;
        } else {
            dos.writeBoolean(false);
            return false;
        }
    }

    @Override
    public Resume doRead(BufferedInputStream bis) throws IOException {
        try (DataInputStream dis = new DataInputStream(bis)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);

            Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            readMapWithException(contacts, (Map m, DataInputStream d) -> this.contactsReader(m, d), dis);
            resume.setContacts(contacts);

            Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
            readMapWithException(sections, (Map m, DataInputStream d) -> this.sectionReader(m, d), dis);
            resume.setSections(sections);
            return resume;
        }
    }

    private <T extends Map> void readMapWithException(T map, ThrowingObjectReader<T> objectReader,
                                                      DataInputStream dis) throws IOException {
        for (int amount = dis.readInt(); amount > 0; amount--) {
            objectReader.accept(map, dis);
        }
    }

    private void contactsReader(Map map, DataInputStream dis) throws IOException {
        map.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
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
                readListWithException(skillsList, (List l, DataInputStream d) -> l.add(dis.readUTF()), dis);
                map.put(sectionType, new SkillsSection(skillsList));
                break;
            case EXPERIENCE:
            case EDUCATION:
                final List<Experience> experienceList = new ArrayList<>();
                readListWithException(experienceList, (List l, DataInputStream d) -> l.add(readExperience(d)), dis);
                map.put(sectionType, new ExperienceSection(experienceList));
                break;
        }
    }

    private <T extends List> void readListWithException(T list, ThrowingObjectReader<T> objectReader,
                                                        DataInputStream dis) throws IOException {
        for (int i = dis.readInt(); i > 0 ; i--) {
            objectReader.accept(list, dis);
        }
    }

    private Experience readExperience(DataInputStream dis) throws IOException {
        String companyName = dis.readUTF();
        Link companyLink = existOptionalField(dis) ? readLink(dis) : null;
        final Company company = new Company(companyName, companyLink);
        final LocalDate startDate = LocalDate.parse(dis.readUTF());
        final LocalDate endDate = LocalDate.parse(dis.readUTF());
        final String shortInfo = dis.readUTF();
        final String detailedInfo = existOptionalField(dis) ? dis.readUTF() : null;
        return new Experience(company, startDate, endDate, shortInfo, detailedInfo);
    }

    private Link readLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), dis.readUTF());
    }

    private <T> boolean existOptionalField(DataInputStream dis) throws IOException {
        return dis.readBoolean();
    }
}

interface ThrowingObjectWriter<T> {
    void accept(T t, DataOutputStream dos) throws IOException;
}

interface ThrowingObjectReader<T> {
    void accept(T t, DataInputStream dis) throws IOException;
}