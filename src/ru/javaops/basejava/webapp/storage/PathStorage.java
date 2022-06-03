package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.serializestrategy.SerializeStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path dir;
    private final SerializeStrategy serializator;

    public PathStorage(String directory, SerializeStrategy serializator) {
        Path dir = Paths.get(directory);
        Objects.requireNonNull(dir, "directory must not be null");
        if (!Files.isDirectory(dir))
            throw new IllegalArgumentException(dir + " is not directory");
        if (!Files.isReadable(dir) || !Files.isWritable(dir))
            throw new IllegalArgumentException(dir + " can't read/write");
        this.dir = dir;
        Objects.requireNonNull(serializator, "Serializator must not be null");
        this.serializator = serializator;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return dir.resolve(uuid);
    }

    @Override
    protected void saveResume(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Can't create file " + path.getFileName(), null, e);
        }
        updateResume(path, r);
    }

    @Override
    protected void updateResume(Path path, Resume r) {
        try {
            doWrite(path, r);
        } catch (IOException e) {
            throw new StorageException("Cant write to " + path.getFileName(), r.getUuid(), e);
        }
    }

    protected void doWrite(Path path, Resume r) throws IOException {
        serializator.serializeOutputStream(new BufferedOutputStream(Files.newOutputStream(path)), r);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("Can't read from " + path, null, e);
        }
    }

    protected Resume doRead(Path path) throws IOException {
        return serializator.deserializeInputStream(new BufferedInputStream(Files.newInputStream(path)));
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can't delete " + path, null, e);
        }
    }

    @Override
    protected boolean isExistSearchKey(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getListResumes() {
        List<Resume> resumeList = new ArrayList<>();
        processPaths(dir, path -> resumeList.add(getResume(path)));
        return resumeList;
    }

    @Override
    public void clear() {
        processPaths(dir, this::deleteResume);
    }

    @Override
    public int size() {
        return processPaths(dir, path -> {
        });
    }

    private int processPaths(Path dir, Consumer<Path> consumer) {
        try (Stream<Path> stream = Files.list(dir)) {
            return (int) stream.peek(consumer).count();
        } catch (IOException e) {
            throw new StorageException("Can't read files", null, e);
        }
    }
}
