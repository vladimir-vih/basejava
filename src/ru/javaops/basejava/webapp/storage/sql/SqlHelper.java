package ru.javaops.basejava.webapp.storage.sql;

import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public static void executeStatement(ConnectionFactory connectionFactory, String statementString,
                                        ThrowingStatementExecutor<PreparedStatement> statementExecutor) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(statementString);
            statementExecutor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException("Can't execute SQL command", e);
        }
    }
}

