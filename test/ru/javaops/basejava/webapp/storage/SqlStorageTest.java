package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static final String dbUrl = Config.getInstance().getDbUrl();
    private static final String dbUser = Config.getInstance().getDbUser();
    private static final String dbPass = Config.getInstance().getDbPass();

    public SqlStorageTest() {
        super(Config.getInstance().getSqlStorage());
    }
}
