package com.github.jcactusdev.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
    T execute(PreparedStatement stmt) throws SQLException;
}
