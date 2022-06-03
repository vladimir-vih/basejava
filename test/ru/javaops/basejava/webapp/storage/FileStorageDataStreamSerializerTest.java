package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.DataStreamSerializer;

public class FileStorageDataStreamSerializerTest extends AbstractStorageTest {
    public FileStorageDataStreamSerializerTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new DataStreamSerializer()));
    }
}