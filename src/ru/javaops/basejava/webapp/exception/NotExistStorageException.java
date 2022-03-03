package ru.javaops.basejava.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + ", doesn't exist.", uuid);
    }
}
