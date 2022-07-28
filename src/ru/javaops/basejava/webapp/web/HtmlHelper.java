package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.model.*;
import ru.javaops.basejava.webapp.storage.Storage;

import java.time.LocalDate;
import java.util.*;

public class HtmlHelper {
    private static final Storage STORAGE = Config.getInstance().getSqlStorage();

    public static List<Resume> getAllResumes() {
        return STORAGE.getAllSorted();
    }

    public static Resume getResume(String uuid) {
        Resume r = STORAGE.get(uuid);
        List<Experience> experienceList = (List<Experience>) r.getSection(SectionType.EXPERIENCE).getBody();
        if (experienceList != null) Collections.sort(experienceList);
        r.addSection(SectionType.EXPERIENCE, new ExperienceSection(experienceList));

        List<Experience> educationList = (List<Experience>) r.getSection(SectionType.EDUCATION).getBody();
        if (educationList != null) Collections.sort(educationList);
        r.addSection(SectionType.EDUCATION, new ExperienceSection(educationList));
        return r;
    }

    public static void deleteResume(String uuid) {
        STORAGE.delete(uuid);
    }

    public static void updateResume(Resume r) {
        STORAGE.update(r);
    }

    public static void saveResume(Resume r) {
        STORAGE.save(r);
    }

    public static String getNewUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean hasContactLink(Map.Entry<ContactType, String> contactEntry) {
        switch (contactEntry.getKey()) {
            case MAIL:
            case SKYPE:
            case LINKEDIN:
            case HOMEPAGE:
            case GIT_HUB:
            case STACK_OVERFLOW:
                return true;
            default:
                return false;
        }
    }

    public static String getContactLink(Map.Entry<ContactType, String> contactEntry) {
        ContactType type = contactEntry.getKey();
        String contactText = contactEntry.getValue();
        switch (type) {
            case MAIL:
                return "mailto:" + contactText;
            case SKYPE:
                return "skype:" + contactText;
            case HOMEPAGE:
            case GIT_HUB:
            case LINKEDIN:
            case STACK_OVERFLOW:
                return contactText;
            default:
                return "";
        }
    }

    public static String getContactImgLink(ContactType type) {
        switch (type) {
            case MOB_NUMBER:
                return "img/phone.png";
            case MAIL:
                return "img/email.png";
            case SKYPE:
                return "img/skype.png";
            case HOMEPAGE:
                return "img/home.png";
            case GIT_HUB:
                return "img/domain.png";
            case LINKEDIN:
                return "img/domain.png";
            case STACK_OVERFLOW:
                return "img/domain.png";
            default:
                return "";
        }
    }

    public static String getEndDateString(Experience experience) {
        return experience.getEndDate().equals(LocalDate.MAX) ? "NOW" : experience.getEndDate().toString();
    }

    public static boolean hasExperienceUrl(Experience experience) {
        return Objects.nonNull(experience.getCompany().getUrl());
    }
}
