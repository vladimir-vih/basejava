package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.JsonSerializer;

public class PathStorageJsonSerializerTest extends AbstractStorageTest {
    public PathStorageJsonSerializerTest() {
        super(new PathStorage(STORAGE_DIR, new JsonSerializer()));
    }
}
