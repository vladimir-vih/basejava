package ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters;

import ru.javaops.basejava.webapp.model.Section;
import ru.javaops.basejava.webapp.model.SectionType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MapXmlAdapter extends XmlAdapter<MapXmlAdapter.MyMapType, Map<SectionType, Section>> {
    @Override
    public Map<SectionType, Section> unmarshal(MyMapType myMapType) {
        if (myMapType == null) {
            return null;
        }
        try {
            Map<SectionType, Section> enumMap = new EnumMap<>(SectionType.class);
            for (MyMapEntry myMapEntry : myMapType.myMapEntryList) {
                enumMap.put(myMapEntry.sectionType, myMapEntry.value);
            }
            return enumMap;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public MyMapType marshal(Map<SectionType, Section> enumMap) {
        if (enumMap == null) {
            return null;
        }
        MyMapType myMapType = new MyMapType();
        for (Map.Entry<SectionType, Section> entry : enumMap.entrySet()) {
            MyMapEntry myMapEntry = new MyMapEntry();
            myMapEntry.sectionType = entry.getKey();
            myMapEntry.value = entry.getValue();
            myMapType.myMapEntryList.add(myMapEntry);
        }
        return myMapType;
    }

    public static class MyMapType {
        @XmlElement(name = "section")
        public List<MyMapEntry> myMapEntryList = new ArrayList<>();
    }

    public static class MyMapEntry {
        @XmlAttribute
        public SectionType sectionType;
        @XmlElement
        public Section value;
    }
}
