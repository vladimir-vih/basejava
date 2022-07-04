package ru.javaops.basejava.webapp.storage.sql;

import java.sql.SQLException;

public interface ThrowingStatementExecutor<T> {
    void execute(T ps) throws SQLException;
}
