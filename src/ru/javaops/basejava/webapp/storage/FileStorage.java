package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.io.*;

public class FileStorage extends AbstractFileStorage {
    private final SerializeStrategy serializator;
    public FileStorage(String directory, SerializeStrategy serializator) {
        super(new File(directory));
        this.serializator = serializator;
    }

    @Override
    protected void doWrite(File file, Resume r) throws IOException {
        serializator.serializeOutputStream(new BufferedOutputStream(new FileOutputStream(file)), r);
    }

    @Override
    protected Resume doRead(File file) throws IOException {
        return serializator.deserializeInputStream(new BufferedInputStream(new FileInputStream(file)));
    }


}
