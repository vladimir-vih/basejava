package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.serializestrategy.SerializeStrategy;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializeStrategy serializator;

    public FileStorage(String dir, SerializeStrategy serializator) {
        File directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " can't read/write");
        }
        this.directory = directory;
        Objects.requireNonNull(serializator, "Serializator must not be null");
        this.serializator = serializator;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void saveResume(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException | SecurityException e) {
            throw new StorageException("Can't create new file ", r.getUuid(), e);
        }
        updateResume(file, r);
    }

    protected void doWrite(File file, Resume r) throws IOException {
        serializator.serializeOutputStream(new BufferedOutputStream(new FileOutputStream(file)), r);
    }

    @Override
    protected void updateResume(File file, Resume r) {
        try {
            doWrite(file, r);
        } catch (IOException e) {
            throw new StorageException("Can't save file ", r.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Can't read file " + file.getName(), null, e);
        }
    }

    protected Resume doRead(File file) throws IOException {
        return serializator.deserializeInputStream(new BufferedInputStream(new FileInputStream(file)));
    }

    @Override
    protected void deleteResume(File file) {
        try {
            file.delete();
        } catch (SecurityException e) {
            throw new StorageException("Can't delete ", file.getName(), e);
        }

    }

    @Override
    protected boolean isExistSearchKey(File file) {
        try {
            return file.exists();
        } catch (SecurityException e) {
            throw new StorageException("Can't read ", file.getName(), e);
        }

    }

    @Override
    protected List<Resume> getListResumes() {
        List<Resume> resumeList = new LinkedList<>();
        proccessFiles(directory, file -> resumeList.add(getResume(file)));
        return resumeList;
    }

    @Override
    public void clear() {
        proccessFiles(directory, this::deleteResume);
    }

    @Override
    public int size() {
        return proccessFiles(directory, file -> {
        });
    }

    private int proccessFiles(File dir, Consumer<File> consumer) {
        String[] fileList = dir.list();
        int count = 0;
        if (fileList != null) {
            for (String name : fileList) {
                File file = new File(dir, name);
                consumer.accept(file);
                count++;
            }
        }
        return count;
    }
}
