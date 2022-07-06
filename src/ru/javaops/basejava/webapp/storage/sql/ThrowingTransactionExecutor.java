package ru.javaops.basejava.webapp.storage.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ThrowingTransactionExecutor<T> {
    T execute(Connection conn) throws SQLException;
}
