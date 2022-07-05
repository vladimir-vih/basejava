package ru.javaops.basejava.webapp.storage.sql;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPass) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("Unable to load DB Driver class.", e);
        }
        connectionFactory = () ->
                DriverManager.getConnection(dbUrl,dbUser, dbPass);
    }

    public Object executeStatement(String statementString,
                                           ThrowingStatementExecutor<PreparedStatement> statementExecutor) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(statementString);
            return statementExecutor.execute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                ServerErrorMessage m = ((PSQLException) e).getServerErrorMessage();
                if (m.getSQLState().equals("23505")) {
                    /*SQLState = 23505
                    Class 23 — Integrity Constraint Violation - unique_violation */
                    throw new ExistStorageException();
                }
            }
            throw new StorageException("Can't execute SQL command", e);
        }
    }
}

