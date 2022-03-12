package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;

public class ListStorage extends AbstractStorage implements Storage {
    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume r) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(r.getUuid())) { throw new ExistStorageException(r.getUuid()); }
        }
        storage.add(r);
    }

    @Override
    public void update(Resume resume) {
        for (Resume r : storage) {
            if (resume.getUuid().equals(r.getUuid())) {
                storage.set(storage.indexOf(r), resume);
                return;
            }
        }
        throw new NotExistStorageException(resume.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) return r;
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (uuid.equals(r.getUuid())) {
                iterator.remove();
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
