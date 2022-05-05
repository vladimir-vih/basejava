package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path dir;

    public AbstractPathStorage(Path dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        if (!Files.isDirectory(dir))
            throw new IllegalArgumentException(dir + " is not directory");
        if (!Files.isReadable(dir) || !Files.isWritable(dir))
            throw new IllegalArgumentException(dir + " can't read/write");
        this.dir = dir;
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

    protected abstract void doWrite(Path path, Resume r) throws IOException;

    @Override
    protected Resume getResume(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("Can't read from " + path, null, e);
        }
    }

    protected abstract Resume doRead(Path path) throws IOException;

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can't delete " + path,null,e);
        }
    }

    @Override
    protected boolean isExistSearchKey(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getListResumes() {
        try (Stream<Path> stream = Files.list(dir)) {
            return stream.map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Can't read files", null, e);
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> stream = Files.list(dir)) {
            stream.forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Can't read files", null, e);
        }

    }

    @Override
    public int size() {
        try (Stream<Path> stream = Files.list(dir)) {
            return (int) stream.count();
        } catch (IOException e) {
            throw new StorageException("Can't read files", null, e);
        }
    }
}
