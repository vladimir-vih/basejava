package ru.javaops.basejava.webapp.storage.serializestrategy;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements SerializeStrategy {
    private final JAXBContext context;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;


    public XmlSerializer() {
        try {
            this.context = JAXBContext.newInstance(Resume.class, SkillsSection.class, ExperienceSection.class,
                    Experience.class, Company.class, Link.class, CharacteristicSection.class);
            this.marshaller = context.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            this.unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new StorageException("Xml serialization error", null, e);
        }
    }

    @Override
    public void doWrite(BufferedOutputStream bos, Resume r) throws IOException {
        try (Writer writer = new OutputStreamWriter(bos, StandardCharsets.UTF_8)) {
            marshaller.marshal(r, writer);
        } catch (JAXBException e) {
            throw new StorageException("Xml serialization error", null, e);
        }
    }

    @Override
    public Resume doRead(BufferedInputStream bis) throws IOException {
        try (Reader reader = new InputStreamReader(bis, StandardCharsets.UTF_8)) {
            return (Resume) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new StorageException("Xml deserialization error", null, e);
        }
    }
}
