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
                final SectionType sectionType = sectionEntry.getKey();
                dos.writeUTF(sectionType.name());
                final Section section = sectionEntry.getValue();
                switch (sectionType.getClassType()) {
                    case ("CharacteristicSection"):
                        final Section<String> characteristicSection = (CharacteristicSection) section;
                        dos.writeUTF(characteristicSection.getBody());
                        break;
                    case ("SkillsSection"):
                        final Section<List<String>> skillsSection = (SkillsSection) section;
                        final List<String> skillsList = skillsSection.getBody();
                        dos.writeInt(skillsList.size());
                        for (String skill : skillsList) dos.writeUTF(skill);
                        break;
                    case ("ExperienceSection"):
                        final Section<List<Experience>> experienceSection = (ExperienceSection) section;
                        final List<Experience> experienceList = experienceSection.getBody();
                        dos.writeInt(experienceList.size());
                        for (Experience experience : experienceList) serializeExperience(experience, dos);
                        break;
                }
            }
        }
    }

    private void serializeExperience(Experience experience, DataOutputStream dos) throws IOException {
        final Company company = experience.getCompany();
        dos.writeUTF(company.getName());
        final Link companyLink = company.getUrl();
        if (serializeOptionalFieldFlag(companyLink, dos)) serializeLink(companyLink, dos);
        dos.writeUTF(experience.getStartDate().toString());
        dos.writeUTF(experience.getEndDate().toString());
        dos.writeUTF(experience.getShortInfo());
        final String detailedInfo = experience.getDetailedInfo();
        if (serializeOptionalFieldFlag(detailedInfo, dos)) dos.writeUTF(detailedInfo);
    }

    private void serializeLink(Link link, DataOutputStream dos) throws IOException {
        dos.writeUTF(link.getName());
        dos.writeUTF(link.getUrl());
    }

    private <T> boolean serializeOptionalFieldFlag(T object, DataOutputStream dos) throws IOException {
        if (object != null) {
            dos.writeInt(1);
            return true;
        } else  {
            dos.writeInt(0);
            return false;
        }
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
                final SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType.getClassType()) {
                    case ("CharacteristicSection"):
                        final Section<String> section = new CharacteristicSection(dis.readUTF());
                        resume.addSection(sectionType, section);
                        break;
                    case ("SkillsSection"):
                        int experienceListSize = dis.readInt();
                        final List<String> skillsList = new ArrayList<>();
                        while (experienceListSize > 0) {
                            skillsList.add(dis.readUTF());
                            experienceListSize--;
                        }
                        resume.addSection(sectionType, new SkillsSection(skillsList));
                        break;
                    case ("ExperienceSection"):
                        int skillsListSize = dis.readInt();
                        final List<Experience> experienceList = new ArrayList<>();
                        while (skillsListSize > 0) {
                            experienceList.add(deserializeExperience(dis));
                            skillsListSize--;
                        }
                        resume.addSection(sectionType, new ExperienceSection(experienceList));
                        break;
                }
                i--;
            }
            return resume;
        }
    }

    private Experience deserializeExperience(DataInputStream dis) throws IOException {
        String companyName = dis.readUTF();
        Link companyLink = existOptionalField(dis) ? deserializeLink(dis) : null;
        final Company company = new Company(companyName, companyLink);
        final LocalDate startDate = LocalDate.parse(dis.readUTF());
        final LocalDate endDate = LocalDate.parse(dis.readUTF());
        final String shortInfo = dis.readUTF();
        final String detailedInfo = existOptionalField(dis) ? dis.readUTF() : null;
        return new Experience(company, startDate, endDate, shortInfo, detailedInfo);
    }

    private Link deserializeLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), dis.readUTF());
    }

    private <T> boolean existOptionalField(DataInputStream dis) throws IOException {
        return dis.readInt() == 1;
    }
}