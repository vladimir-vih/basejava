package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.*;
import ru.javaops.basejava.webapp.storage.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
            insertContactsToDB(conn, r);
            insertSectionsToDB(conn, r);
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
            deleteByUuid(conn, uuid, "contact");
            insertContactsToDB(conn, resume);
            deleteByUuid(conn, uuid, "section");
            insertSectionsToDB(conn, resume);
            return null;
        });
        log.info("Updated resume " + uuid);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(conn -> {
            Resume r;
            try (PreparedStatement psContacts = conn.prepareStatement(
                    "       SELECT r.full_name, c.type, c.value " +
                            "     FROM resume r " +
                            "LEFT JOIN contact c " +
                            "       ON c.resume_uuid = r.uuid " +
                            "    WHERE r.uuid = ?"
            )) {
                psContacts.setString(1, uuid);
                ResultSet rs = psContacts.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                final String fullName = rs.getString("full_name");
                r = new Resume(uuid, fullName);
                do {
                    addContactToResume(rs, r);
                } while (rs.next());
            }
            try (PreparedStatement psSections = conn.prepareStatement(
                    "       SELECT s.type, s.value " +
                            "     FROM section s " +
                            "    WHERE s.resume_uuid = ?"
            )) {
                psSections.setString(1, uuid);
                ResultSet rsSections = psSections.executeQuery();
                while (rsSections.next()) {
                    addSectionToResume(rsSections, r);
                }
            }
            log.info("Got resume UUID: " + uuid);
            return r;
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
            Map<String, Resume> uuidResumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement(
                    "       SELECT full_name, TRIM(TRAILING uuid) uuid " +
                            "     FROM resume " +
                            " ORDER BY full_name, uuid")) {
                ResultSet rsResume = ps.executeQuery();
                if (!rsResume.next()) {
                    return new ArrayList<>(uuidResumeMap.values());
                }
                do {
                    String uuid = rsResume.getString("uuid");
                    uuidResumeMap.put(uuid, new Resume(uuid, rsResume.getString("full_name")));
                } while (rsResume.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "       SELECT TRIM(TRAILING c.resume_uuid) uuid, c.type, c.value, 'contact' table_name " +
                            "     FROM contact c " +
                            "    UNION ALL" +
                            "   SELECT TRIM(TRAILING s.resume_uuid) uuid, s.type, s.value, 'section' table_name " +
                            "     FROM section s ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = uuidResumeMap.get(rs.getString("uuid"));
                    switch (rs.getString("table_name")) {
                        case "contact":
                            addContactToResume(rs, r);
                            break;
                        case "section" :
                            addSectionToResume(rs, r);
                            break;
                    }
                    uuidResumeMap.put(r.getUuid(),r);
                }
            }
            log.info("Got all resumes sorted");
            return new LinkedList<>(uuidResumeMap.values());
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

    private void insertContactsToDB(Connection conn, Resume r) throws SQLException {
        if (r.getContacts().size() == 0) return;
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

    private void insertSectionsToDB(Connection conn, Resume r) throws SQLException {
        if (r.getSections().size() == 0) return;
        final String uuid = r.getUuid();
        try (PreparedStatement psInsert = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        Section<String> characteristicSection = entry.getValue();
                        psInsert.setString(1, uuid);
                        psInsert.setString(2, entry.getKey().name());
                        psInsert.setString(3, characteristicSection.getBody());
                        psInsert.addBatch();
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        Section<List<String>> skillsSection = entry.getValue();
                        StringBuilder skillsSB = new StringBuilder();
                        for (String s : skillsSection.getBody()) {
                            skillsSB.append(s).append("\n");
                        }
                        psInsert.setString(1, uuid);
                        psInsert.setString(2, entry.getKey().name());
                        psInsert.setString(3, skillsSB.toString());
                        psInsert.addBatch();
                        break;
                }
            }
            psInsert.executeBatch();
        }
    }

    private void addContactToResume(ResultSet rs, Resume r) throws SQLException {
        final String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")),
                    value);
        }
    }

    private void addSectionToResume(ResultSet rs, Resume r) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("type"));
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE: {
                r.addSection(sectionType, new CharacteristicSection(rs.getString("value")));
                break;
            }
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                String[] skillsArr = rs.getString("value").split("\n");
                List<String> skillsList = Arrays.asList(skillsArr);
                r.addSection(sectionType, new SkillsSection(skillsList));
                break;
            }
        }
    }

    private void deleteByUuid(Connection conn, String uuid, String table) throws SQLException {
        String statement = "DELETE FROM " + table + " WHERE resume_uuid = ?";
        try (PreparedStatement psDelete = conn.prepareStatement(statement)) {
//            psDelete.setString(1, table);
            psDelete.setString(1, uuid);
            psDelete.execute();
        }
    }
}
