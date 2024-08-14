package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.ContactType;
import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.model.Section;
import com.github.jcactusdev.model.SectionType;
import com.github.jcactusdev.sql.SqlHelper;
import com.github.jcactusdev.util.JsonParser;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword));
    }

    @Override
    public void save(Resume object) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, object.getUuid());
                ps.setString(2, object.getFullName());
                ps.execute();
            }
            insertContacts(conn, object);
            insertSections(conn, object);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume = null;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new NoSuchElementException("No such resume with uuid " + uuid);
                    }
                    resume = new Resume(uuid, rs.getString("full_name"));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        addContact(rs, resume);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        addSection(rs, resume);
                    }
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = resumes.get(uuid);
                    if (resume == null) {
                        resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void update(Resume object) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, object.getFullName());
                ps.setString(2, object.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("Resume not exists in database");
                }
            }
            deleteContact(conn, object);
            deleteSection(conn, object);
            insertContacts(conn, object);
            insertSections(conn, object);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Resume not exists");
            }
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume object) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : object.getContacts().entrySet()) {
                ps.setString(1, object.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteContact(Connection conn, Resume object) {
        deleteAttribute(conn, object, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void insertSections(Connection conn, Resume object) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> entry : object.getSections().entrySet()) {
                ps.setString(1, object.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, JsonParser.write(entry.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteSection(Connection conn, Resume object) {
        deleteAttribute(conn, object, "DELETE FROM section WHERE resume_uuid = ?");
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            Section section = JsonParser.read(content, Section.class);
            resume.addSection(type, section);
        }
    }

    private void deleteAttribute(Connection conn, Resume object, String sql) {
        sqlHelper.<Void>execute(sql, ps -> {
            ps.setString(1, object.getUuid());
            ps.execute();
            return null;
        });
    }

}
