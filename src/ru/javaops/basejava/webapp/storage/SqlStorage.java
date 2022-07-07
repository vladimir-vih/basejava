package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, uuid);
                ps.setString(2, fullName);
                ps.execute();
            }
            insertContact(conn, r);
            return null;
        });
        log.info("Saved resume " + uuid + " to RESUME table");
    }

    @Override
    public void update(Resume resume) {
        final String uuid = resume.getUuid();
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement psFullName = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                psFullName.setString(1, resume.getFullName());
                psFullName.setString(2, uuid);
                if (psFullName.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement psDeleteContacts = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                psDeleteContacts.setString(1, uuid);
                psDeleteContacts.execute();
            }
            insertContact(conn, resume);
            return null;
        });
        log.info("Updated resume " + uuid);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "       SELECT r.full_name, c.type, c.value " +
                            "     FROM resume r " +
                            "LEFT JOIN contact c " +
                            "       ON c.resume_uuid = r.uuid " +
                            "    WHERE r.uuid = ?"
            )) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                final String fullName = rs.getString("full_name");
                Resume r = new Resume(uuid, fullName);
                do {
                    addContact(rs, r);
                } while (rs.next());
                log.info("Got resume UUID: " + uuid + ", FULL NAME: " + fullName);
                return r;
            }
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
        /*Get all resumes by one query*/
//        return sqlHelper.executeStatement(
//                "       SELECT TRIM(TRAILING r.uuid) uuid, r.full_name, c.type, c.value " +
//                        "     FROM resume r " +
//                        "LEFT JOIN contact c " +
//                        "       ON c.resume_uuid = r.uuid " +
//                        " ORDER BY full_name, uuid"
//                , ps -> {
//                    ResultSet rs = ps.executeQuery();
//                    Map<String, Resume> uuidResumeMap = new LinkedHashMap<>();
//                    while (rs.next()) {
//                        final String uuid = rs.getString("uuid");
//                        Resume resume = uuidResumeMap.get(uuid);
//                        if (resume == null) {
//                            resume = new Resume(uuid, rs.getString("full_name"));
//                            uuidResumeMap.put(uuid, resume);
//                        }
//                        addContact(rs, resume);
//                        uuidResumeMap.put(uuid, resume);
//                    }
//                    return new LinkedList<>(uuidResumeMap.values());
//                });

        /*Get Resumes one-by-one*/
        return sqlHelper.executeTransaction(conn -> {
            List<Resume> resumeList = new LinkedList<>();
            List<String> uuidList = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(
                    "       SELECT full_name, uuid " +
                            "     FROM resume " +
                            " ORDER BY full_name, uuid")) {
                ResultSet rsUuid = ps.executeQuery();
                if (!rsUuid.next()) {
                    return resumeList;
                }
                do {
                    uuidList.add(rsUuid.getString("uuid"));
                } while (rsUuid.next());
            }
            for (String tempUuid : uuidList) {
                try (PreparedStatement psResume = conn.prepareStatement(
                        "       SELECT TRIM(TRAILING r.uuid) uuid, r.full_name, c.type, c.value " +
                                "     FROM resume r " +
                                "LEFT JOIN contact c " +
                                "       ON c.resume_uuid = r.uuid " +
                                "    WHERE r.uuid = ?")) {
                    psResume.setString(1, tempUuid);
                    ResultSet rsResume = psResume.executeQuery();
                    rsResume.next();
                    final String uuid = rsResume.getString("uuid");
                    final String fullName = rsResume.getString("full_name");
                    Resume r = new Resume(uuid, fullName);
                    do {
                        addContact(rsResume, r);
                    } while (rsResume.next());
                    resumeList.add(r);
                }
            }
            log.info("Got all resumes sorted");
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

    private void insertContact(Connection conn, Resume r) throws SQLException {
        final String uuid = r.getUuid();
        try (PreparedStatement psInsert = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                psInsert.setString(1, uuid);
                psInsert.setString(2, entry.getKey().name());
                psInsert.setString(3, entry.getValue());
                psInsert.addBatch();
            }
            psInsert.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        final String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")),
                    value);
        }
    }
}
