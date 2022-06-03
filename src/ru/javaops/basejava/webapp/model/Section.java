package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

//@XmlJavaTypeAdapter(SectionXmlAdapter.class)
@XmlAccessorType(XmlAccessType.FIELD)
public interface Section<T> {
    T getBody();
}
