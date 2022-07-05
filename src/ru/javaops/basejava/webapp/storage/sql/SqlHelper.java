package ru.javaops.basejava.webapp.storage.sql;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("Unable to load DB Driver class.", e);
        }
    }
    private static final Config config = Config.getInstance();
    private static final ConnectionFactory connectionFactory = () ->
        DriverManager.getConnection(config.getDbUrl(),config.getDbUser(), config.getDbPass());

    public static Object executeStatement(String statementString,
                                           ThrowingStatementExecutor<PreparedStatement> statementExecutor) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(statementString);
            return statementExecutor.execute(ps);
        } catch (SQLException e) {
            if (e.getClass() == PSQLException.class) {
                ServerErrorMessage m = ((PSQLException) e).getServerErrorMessage();
                if (m.getDetail().contains("уже существует")){
                    throw new ExistStorageException();
                }
            }
            throw new StorageException("Can't execute SQL command", e);
        }
    }
}

