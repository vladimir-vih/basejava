package ru.javaops.basejava.webapp.storage.serializestrategy;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStrategy implements SerializeStrategy {

    @Override
    public void serializeOutputStream(BufferedOutputStream bos, Resume r) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume deserializeInputStream(BufferedInputStream bis) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't find class Resume for the deserialization", null, e);
        }
    }
}
