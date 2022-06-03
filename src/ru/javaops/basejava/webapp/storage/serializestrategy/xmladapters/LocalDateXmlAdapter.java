package ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String dateString) {
        if (dateString == null) return null;
        return LocalDate.parse(dateString);
    }

    @Override
    public String marshal(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.toString();
    }
}
