package ru.javaops.basejava.webapp.storage.sql;

import java.sql.SQLException;

public interface ThrowingStatementExecutor<T> {
    Object execute(T ps) throws SQLException;
}
