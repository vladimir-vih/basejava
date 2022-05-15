package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serializestrategy.ObjectStreamStrategy;

public class PathStorageObjectStreamStrategyTest extends AbstractStorageTest{
    public PathStorageObjectStreamStrategyTest() {
        super(new PathStorage("C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp" +
                "\\storage\\files", new ObjectStreamStrategy()));
    }
}