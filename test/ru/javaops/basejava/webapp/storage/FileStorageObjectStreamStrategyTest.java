package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.ObjectStreamStrategy;

public class FileStorageObjectStreamStrategyTest extends AbstractStorageTest {
    public FileStorageObjectStreamStrategyTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new ObjectStreamStrategy()));
    }
}