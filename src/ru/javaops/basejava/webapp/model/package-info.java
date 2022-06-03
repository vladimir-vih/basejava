@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = MapXmlAdapter.class, type = Map.class),
        @XmlJavaTypeAdapter(value = SectionXmlAdapter.class, type = Section.class),
        @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class, type = LocalDate.class)})

package ru.javaops.basejava.webapp.model;

import ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters.LocalDateXmlAdapter;
import ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters.MapXmlAdapter;
import ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters.SectionXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.util.Map;