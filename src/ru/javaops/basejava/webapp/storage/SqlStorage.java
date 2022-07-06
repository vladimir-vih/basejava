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
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                insertContact(conn, uuid, entry.getKey(), entry.getValue());
            }
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

            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                int updateResult;
                try (PreparedStatement psUpdateContacts = conn.prepareStatement("UPDATE contact SET value = ? WHERE resume_uuid = ? and  type = ?")) {
                    psUpdateContacts.setString(1, entry.getValue());
                    psUpdateContacts.setString(2, uuid);
                    psUpdateContacts.setString(3, entry.getKey().name());
                    updateResult = psUpdateContacts.executeUpdate();
                }
                if (updateResult == 0) {
                    insertContact(conn, uuid, entry.getKey(), entry.getValue());
                }
            }
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
                    r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
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
                        r.addContact(ContactType.valueOf(rsResume.getString("type")),
                                rsResume.getString("value"));
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

    private void insertContact(Connection conn, String uuid, ContactType type, String value) throws SQLException {
        try (PreparedStatement psInsert = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            psInsert.setString(1, uuid);
            psInsert.setString(2, type.name());
            psInsert.setString(3, value);
            psInsert.execute();
        }
    }
}
