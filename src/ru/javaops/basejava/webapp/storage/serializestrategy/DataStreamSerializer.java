package ru.javaops.basejava.webapp.storage.serializestrategy;

import ru.javaops.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializeStrategy {
    @Override
    public void serializeOutputStream(BufferedOutputStream bos, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                dos.writeUTF(sectionEntry.getKey().name());
                final Section section = sectionEntry.getValue();
                final String className = sectionEntry.getValue().getClass().getSimpleName();
                dos.writeUTF(className);
                switch (className) {
                    case ("CharacteristicSection"):
                        final Section<String> characteristicSection = (CharacteristicSection) section;
                        dos.writeUTF(characteristicSection.getBody());
                        break;
                    case ("SkillsSection"):
                        final Section<List<String>> skillsSection = (SkillsSection) section;
                        serializeListSection(skillsSection, (String s, DataOutputStream y) -> y.writeUTF(s), dos);
                        break;
                    case ("ExperienceSection"):
                        final Section<List<Experience>> experienceSection = (ExperienceSection) section;
                        serializeListSection(experienceSection, this::serializeExperience, dos);
                        break;
                }
            }
        }
    }

    private void serializeExperience(Experience experience, DataOutputStream dos) throws IOException {
        final Company company = experience.getCompany();
        dos.writeUTF(company.getName());
        serializeOptionalField(company.getUrl(), this::serializeLink, dos);
        dos.writeUTF(experience.getStartDate().toString());
        dos.writeUTF(experience.getEndDate().toString());
        dos.writeUTF(experience.getShortInfo());
        serializeOptionalField(experience.getDetailedInfo(), (String s, DataOutputStream y) -> y.writeUTF(s), dos);
    }

    private void serializeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());
    }

    private <T> void serializeListSection(Section<List<T>> section,
                                          ThrowingObjectWriter<T, DataOutputStream, IOException> objectWriter,
                                          DataOutputStream dos) throws IOException {
        final List<T> list = section.getBody();
        dos.writeInt(list.size());
        for (T object : list) {
            objectWriter.accept(object, dos);
        }
    }

    private <T> void serializeOptionalField(T object,
                                            ThrowingObjectWriter<T, DataOutputStream, IOException> objectWriter,
                                            DataOutputStream dos) throws IOException {
        if (object != null) {
            dos.writeInt(1);
            objectWriter.accept(object, dos);
        } else dos.writeInt(0);
    }


    @Override
    public Resume deserializeInputStream(BufferedInputStream bis) throws IOException {
        try (DataInputStream dis = new DataInputStream(bis)) {
            String uuid = dis.readUTF();
            String fullname = dis.readUTF();
            Resume resume = new Resume(uuid, fullname);
            int i = dis.readInt();
            while (i > 0) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                i--;
            }
            i = dis.readInt();
            while (i > 0) {
                final String sectionName = dis.readUTF();
                final String secionClassName = dis.readUTF();
                switch (secionClassName) {
                    case ("CharacteristicSection"):
                        final Section<String> section = new CharacteristicSection(dis.readUTF());
                        resume.addSection(SectionType.valueOf(sectionName), section);
                        break;
                    case ("SkillsSection"):
                        resume.addSection(SectionType.valueOf(sectionName), new SkillsSection(deserializeList(DataInput::readUTF, dis)));
                        break;
                    case ("ExperienceSection"):
                        resume.addSection(SectionType.valueOf(sectionName), new ExperienceSection(deserializeList(this::deserializeExperience, dis)));
                        break;
                }
                i--;
            }
            return resume;
        }
    }

    private Experience deserializeExperience(DataInputStream dis) throws IOException {
        final Company company = new Company(dis.readUTF(), deserializeOptionalField(this::deserializeLink, dis));
        final LocalDate startDate = LocalDate.parse(dis.readUTF());
        final LocalDate endDate = LocalDate.parse(dis.readUTF());
        final String shortInfo = dis.readUTF();
        final String detailedInfo = deserializeOptionalField(DataInput::readUTF, dis);
        return new Experience(company, startDate, endDate, shortInfo, detailedInfo);
    }

    private Link deserializeLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), dis.readUTF());
    }

    private <T> T deserializeOptionalField(ThrowingObjectReader<T, DataInputStream, IOException> objectReader, DataInputStream dis) throws IOException {
        if (dis.readInt() == 1) {
            return objectReader.accept(dis);
        } else return null;
    }

    private <T> List<T> deserializeList(ThrowingObjectReader<T, DataInputStream, IOException> objectReader, DataInputStream dis) throws IOException {
        int listSize = dis.readInt();
        final List<T> list = new ArrayList<>();
        while (listSize > 0) {
            list.add(objectReader.accept(dis));
            listSize--;
        }
        return list;
    }
}

interface ThrowingObjectWriter<T, OutputStream, E extends Exception> {
    void accept(T t, OutputStream outputStream) throws E;
}

interface ThrowingObjectReader<T, InputStream, E extends Exception> {
    T accept(InputStream inputStream) throws E;
}