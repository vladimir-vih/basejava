package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathStorage extends AbstractPathStorage {
    private final SerializeStrategy serializator;

    public PathStorage(String dir, SerializeStrategy serializator) {
        super(Paths.get(dir));
        this.serializator = serializator;
    }

    @Override
    protected void doWrite(Path path, Resume r) throws IOException {
        serializator.serializeOutputStream(new BufferedOutputStream(Files.newOutputStream(path)), r);
    }

    @Override
    protected Resume doRead(Path path) throws IOException {
        return serializator.deserializeInputStream(new BufferedInputStream(Files.newInputStream(path)));
    }
}
