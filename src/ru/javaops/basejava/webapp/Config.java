package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.storage.SqlStorage;
import ru.javaops.basejava.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS_FILE = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Storage sqlStorage;
    private final String storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPass;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS_FILE)) {
            Properties PROPS = new Properties();
            PROPS.load(is);
            storageDir = PROPS.getProperty("storage.dir");
            dbUrl = PROPS.getProperty("db.url");
            dbUser = PROPS.getProperty("db.user");
            dbPass = PROPS.getProperty("db.password");
            sqlStorage = new SqlStorage();
        } catch (IOException e) {
            throw new IllegalStateException("Can't load config file", e);
        }
    }

    public String getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }
}
