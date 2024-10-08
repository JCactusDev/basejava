package com.github.jcactusdev.sql;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    public static RuntimeException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            // http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new RuntimeException("23505 - unique_violation", e);
            }
        }
        return new RuntimeException(e);
    }
}
