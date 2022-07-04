package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.ObjectStreamStrategy;

public class FileStorageObjectStreamStrategyTest extends AbstractStorageTest {
    public FileStorageObjectStreamStrategyTest() {
        super(new FileStorage(
                STORAGE_DIR, new ObjectStreamStrategy()));
    }
}