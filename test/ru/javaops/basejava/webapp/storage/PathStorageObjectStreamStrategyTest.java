package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.ObjectStreamStrategy;

public class PathStorageObjectStreamStrategyTest extends AbstractStorageTest {
    public PathStorageObjectStreamStrategyTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}