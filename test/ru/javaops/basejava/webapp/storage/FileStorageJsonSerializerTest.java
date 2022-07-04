package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.JsonSerializer;

public class FileStorageJsonSerializerTest extends AbstractStorageTest {
    public FileStorageJsonSerializerTest() {
        super(new FileStorage(
                STORAGE_DIR, new JsonSerializer()));
    }
}