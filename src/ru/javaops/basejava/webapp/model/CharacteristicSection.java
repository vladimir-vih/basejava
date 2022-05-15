package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class CharacteristicSection implements Section<String>, Serializable {
    private String body;

    CharacteristicSection(){}

    public CharacteristicSection(String body) {
        Objects.requireNonNull(body, "Section can't be null");
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacteristicSection that = (CharacteristicSection) o;
        return getBody().equals(that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody());
    }
}
