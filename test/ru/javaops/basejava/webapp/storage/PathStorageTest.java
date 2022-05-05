package ru.javaops.basejava.webapp.storage;

public class PathStorageTest extends AbstractPathStorageTest{
    public PathStorageTest() {
        super(new PathStorage("C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp" +
                "\\storage\\files", new ObjectStreamStrategy()));
    }
}