package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.sql.ConnectionFactory;
import ru.javaops.basejava.webapp.storage.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    SqlStorage(String dbUrl, String dbUser, String dbPass) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    @Override
    public void clear() {
        SqlHelper.executeStatement(connectionFactory, "DELETE FROM resume",
                PreparedStatement::execute);
        log.info("Cleared RESUME table");
    }

    @Override
    public void save(Resume r) {
        final String uuid = r.getUuid();
        if (isExistResume(uuid)) throw new ExistStorageException(uuid);
        final String fullName = r.getFullName();
        SqlHelper.executeStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, fullName);
                    ps.execute();
                });
        log.info("Saved resume " + uuid + " to RESUME table");
    }

    @Override
    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        if (!isExistResume(uuid)) throw new NotExistStorageException(uuid);
        final String fullName = resume.getFullName();
        SqlHelper.executeStatement(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, fullName);
                    ps.setString(2, uuid);
                    ps.execute();
                });
        log.info("Updated resume " + uuid);
    }

    @Override
    public Resume get(String uuid) {
        if (!isExistResume(uuid)) throw new NotExistStorageException(uuid);
        final String[] fullNameArr = new String[1];
        SqlHelper.executeStatement(connectionFactory, "SELECT full_name FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    fullNameArr[0] = rs.getString("full_name");
                });
        final String fullName = fullNameArr[0];
        log.info("Got resume UUID: " + uuid + ", FULL NAME: " + fullName);
        return new Resume(uuid, fullName);
    }

    @Override
    public void delete(String uuid) {
        if (!isExistResume(uuid)) throw new NotExistStorageException(uuid);
        SqlHelper.executeStatement(connectionFactory, "DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ps.execute();
                });
        log.info("Deleted resume " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        final List<Resume> resumeList = new LinkedList<>();
        if (size() == 0) return resumeList;
        SqlHelper.executeStatement(connectionFactory,
                "SELECT full_name, TRIM(TRAILING FROM uuid) uuid FROM resume ORDER BY full_name asc, uuid asc",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                });
        log.info("Completed operation getAllSorted");
        return resumeList;
    }

    @Override
    public int size() {
        final int[] sizeArr = new int[1];
        SqlHelper.executeStatement(connectionFactory, "SELECT COUNT(DISTINCT uuid) amount FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new StorageException("Can't count rows in RESUME table");
                    } else sizeArr[0] = rs.getInt("amount");
                });
        return sizeArr[0];
    }

    private boolean isExistResume(String uuid) {
        final boolean[] resultArr = new boolean[1];
        SqlHelper.executeStatement(connectionFactory,
                "SELECT full_name FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1,uuid);
                    ResultSet rs = ps.executeQuery();
                    resultArr[0] = rs.next();
                });
        return resultArr[0];
    }
}
