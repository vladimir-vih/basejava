package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final Logger log = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPass) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPass);
    }

    @Override
    public void clear() {
        sqlHelper.executeStatement("DELETE FROM resume",
                PreparedStatement::execute);
        log.info("Cleared RESUME table");
    }

    @Override
    public void save(Resume r) {
        final String uuid = r.getUuid();
        final String fullName = r.getFullName();
        sqlHelper.executeStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, fullName);
                    ps.execute();
                    return null;
                });
        log.info("Saved resume " + uuid + " to RESUME table");
    }

    @Override
    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        final String fullName = resume.getFullName();
        sqlHelper.executeStatement("UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, fullName);
                    ps.setString(2, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
        log.info("Updated resume " + uuid);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeStatement("SELECT full_name FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) throw new NotExistStorageException(uuid);
                    String fullName = rs.getString("full_name");
                    log.info("Got resume UUID: " + uuid + ", FULL NAME: " + fullName);
                    return new Resume(uuid, fullName);
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeStatement("DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
        log.info("Deleted resume " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeStatement("SELECT full_name, TRIM(TRAILING FROM uuid) uuid FROM resume ORDER BY full_name asc, uuid asc",
                ps -> {
                    final List<Resume> resumeList = new LinkedList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    log.info("Completed operation getAllSorted");
                    return resumeList;
                });
    }

    @Override
    public int size() {
        return sqlHelper.executeStatement("SELECT COUNT(DISTINCT uuid) amount FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    log.info("Calculated storage size.");
                    return rs.next() ? rs.getInt("amount") : 0;
                });
    }
}
