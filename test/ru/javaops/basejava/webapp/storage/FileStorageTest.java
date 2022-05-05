package ru.javaops.basejava.webapp.storage;

public class FileStorageTest extends AbstractFileStorageTest {
    public FileStorageTest() {
        super(new FileStorage(
                "C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp\\storage" +
                        "\\files", new ObjectStreamStrategy()));
    }
}