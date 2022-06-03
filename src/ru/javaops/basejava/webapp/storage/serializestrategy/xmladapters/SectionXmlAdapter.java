package ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters;

import ru.javaops.basejava.webapp.model.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class SectionXmlAdapter extends XmlAdapter<SectionXmlAdapter.AdaptedSection, Section<?>> {


    @Override
    public Section unmarshal(AdaptedSection adaptedSection) {
        if (adaptedSection == null) {
            return null;
        }
        if (adaptedSection.characteristicSectionBody != null) {
            return new CharacteristicSection(adaptedSection.characteristicSectionBody);
        } else if (adaptedSection.skillsSectionBody != null) {
            return new SkillsSection(adaptedSection.skillsSectionBody);
        } else if (adaptedSection.experienceSectionBody != null) {
            return new ExperienceSection(adaptedSection.experienceSectionBody);
        }
        return null;
    }

    @Override
    public AdaptedSection marshal(Section section) {
        if (section == null) {
            return null;
        }
        AdaptedSection adaptedSection = new AdaptedSection();
        if (section instanceof CharacteristicSection) {
            CharacteristicSection characteristicSection = (CharacteristicSection) section;
            adaptedSection.characteristicSectionBody = characteristicSection.getBody();
        } else if (section instanceof SkillsSection) {
            SkillsSection skillsSection = (SkillsSection) section;
            adaptedSection.skillsSectionBody = skillsSection.getBody();
        } else if (section instanceof ExperienceSection) {
            ExperienceSection experienceSection = (ExperienceSection) section;
            adaptedSection.experienceSectionBody = experienceSection.getBody();
        }
        return adaptedSection;
    }

    public static class AdaptedSection {
        @XmlElement(name = "characteristics")
        public String characteristicSectionBody;

        @XmlElement(name = "skills")
        public List<String> skillsSectionBody;

        @XmlElement(name = "experience")
        public List<Experience> experienceSectionBody;

    }
}
