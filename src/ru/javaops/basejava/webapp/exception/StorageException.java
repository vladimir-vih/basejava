package ru.javaops.basejava.webapp.exception;

public class StorageException extends RuntimeException {
    private String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
        this.uuid = null;
    }

    public StorageException(String message) {
        super(message);
        this.uuid = null;
    }

    public StorageException(Exception e) {
        super(e);
    }
}
