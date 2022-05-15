package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.XmlSerializer;

public class FileStorageXmlSerializerTest extends AbstractStorageTest {
    public FileStorageXmlSerializerTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new XmlSerializer()));
    }
}