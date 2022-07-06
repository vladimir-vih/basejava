package ru.javaops.basejava.webapp.storage.sql;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {}

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            ServerErrorMessage m = ((PSQLException) e).getServerErrorMessage();
            if (m.getSQLState().equals("23505")) {
                    /*SQLState = 23505
                    Class 23 — Integrity Constraint Violation - unique_violation */
                return new ExistStorageException();
            }
        }
        return new StorageException(e);
    }
}
