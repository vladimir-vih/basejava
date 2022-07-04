package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.XmlSerializer;

public class PathStorageXmlSerializerTest extends AbstractStorageTest {
    public PathStorageXmlSerializerTest() {
        super(new PathStorage(STORAGE_DIR, new XmlSerializer()));
    }
}
