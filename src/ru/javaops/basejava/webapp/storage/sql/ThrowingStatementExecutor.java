package ru.javaops.basejava.webapp.storage.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ThrowingStatementExecutor<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
