package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.ContactType;
import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.sql.SqlHelper;

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
            try (PreparedStatement ps = conn.prepareStatement(getSqlInsertResume())) {
                ps.setString(1, object.getUUID());
                ps.setString(2, object.getFullName());
                ps.execute();
            }
            insertContact(conn, object);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(getSqlGet(), ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(rs, resume);
            }
            while (rs.next());
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(getSqlGetAll(), ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.get(uuid);
                if(resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, resume);
                }
                addContact(rs, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public void update(Resume object) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(getSqlUpdate())) {
                ps.setString(1, object.getFullName());
                ps.setString(2, object.getUUID());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("Resume not exists in database");
                }
            }
            deleteContact(conn, object);
            insertContact(conn, object);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute(getSqlResumeDelete(), ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Resume not exists");
            }
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute(getSqlClear());
    }

    @Override
    public int size() {
        return sqlHelper.execute(getSqlSize(), ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume object) {
        try (PreparedStatement ps = conn.prepareStatement(getSqlInsertContact())) {
            for (Map.Entry<ContactType, String> entry : object.getContacts().entrySet()) {
                ps.setString(1, object.getUUID());
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
        sqlHelper.<Void>execute(getSqlContactDelete(), ps -> {
            ps.setString(1, object.getUUID());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.setContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private static String getSqlInsertResume() {
        return "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
    }

    private static String getSqlInsertContact() {
        return "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)";
    }

    private String getSqlGet() {
        return "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?";
    }

    private static String getSqlGetAll() {
        return "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid";
    }

    private static String getSqlUpdate() {
        return "UPDATE resume SET full_name = ? WHERE uuid = ?";
    }

    private static String getSqlResumeDelete() {
        return "DELETE FROM resume WHERE uuid = ?";
    }

    private static String getSqlContactDelete() {
        return "DELETE FROM contact WHERE resume_uuid = ?";
    }

    private static String getSqlClear() {
        return "DELETE FROM resume";
    }

    private static String getSqlSize() {
        return "SELECT count(*) FROM resume";
    }

}
