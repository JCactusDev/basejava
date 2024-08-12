package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.sql.ConnectionFactory;
import com.github.jcactusdev.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword));
    }

    @Override
    public void save(Resume object) {
        sqlHelper.<Void>execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, object.getUUID());
            ps.setString(2, object.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                return null;
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while(rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public void update(Resume object) {
        sqlHelper.<Void>execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, object.getFullName());
            ps.setString(2, object.getUUID());
            if(ps.executeUpdate() == 0) {
                throw new RuntimeException("Resume not exists");
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if(ps.executeUpdate() == 0) {
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
}
