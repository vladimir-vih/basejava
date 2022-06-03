package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.JsonSerializer;

public class FileStorageJsonSerializerTest extends AbstractStorageTest {
    public FileStorageJsonSerializerTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new JsonSerializer()));
    }
}