package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.DataStreamSerializer;

public class PathStorageDataStreamSerializerTest extends AbstractStorageTest {
    public PathStorageDataStreamSerializerTest() {
        super(new PathStorage("C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp" +
                "\\storage\\files", new DataStreamSerializer()));
    }
}
