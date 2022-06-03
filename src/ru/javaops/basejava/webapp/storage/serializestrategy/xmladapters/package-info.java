@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = MapXmlAdapter.class, type = Map.class),
        @XmlJavaTypeAdapter(value = SectionXmlAdapter.class, type = Section.class),
        @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class, type = LocalDate.class
        )})

package ru.javaops.basejava.webapp.storage.serializestrategy.xmladapters;

import ru.javaops.basejava.webapp.model.Section;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.util.Map;