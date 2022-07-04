package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.DataStreamSerializer;

public class FileStorageDataStreamSerializerTest extends AbstractStorageTest {
    public FileStorageDataStreamSerializerTest() {
        super(new FileStorage(
                STORAGE_DIR, new DataStreamSerializer()));
    }
}