package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " can't read/write");
        }
        this.directory = directory;
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

    protected abstract void doWrite(File file, Resume r) throws IOException;

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
            throw new StorageException("Can't read ", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

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
        String[] fileList = directory.list();
        if (fileList != null) {
            for (String name : fileList) {
                try {
                    resumeList.add(doRead(new File(directory, name)));
                } catch (IOException e) {
                    throw new StorageException("Can't read ", name, e);
                }
            }
        }
        return resumeList;
    }

    @Override
    public void clear() {
        String[] fileList = directory.list();
        if (fileList != null) {
            for (String name : fileList) {
                try {
                    File file = new File(directory, name);
                    file.delete();
                } catch (SecurityException e) {
                    throw new StorageException("Can't delete ", name, e);
                }
            }
        }
    }

    @Override
    public int size() {
        String[] fileList = directory.list();
        if (fileList == null) {
            return 0;
        } else {
            return fileList.length;
        }
    }
}
