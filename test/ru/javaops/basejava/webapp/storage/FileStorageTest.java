package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.ObjectStreamStrategy;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new ObjectStreamStrategy()));
    }
}