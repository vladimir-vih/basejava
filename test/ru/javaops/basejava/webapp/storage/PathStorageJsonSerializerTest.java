package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.JsonSerializer;

public class PathStorageJsonSerializerTest extends AbstractStorageTest {
    public PathStorageJsonSerializerTest() {
        super(new PathStorage("C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp" +
                "\\storage\\files", new JsonSerializer()));
    }
}
