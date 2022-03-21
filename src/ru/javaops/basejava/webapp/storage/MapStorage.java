package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveResume(Object uuid, Resume r) {
        storage.put((String) uuid, r);
    }

    @Override
    protected void updateResume(Object uuid, Resume r) {
        storage.put((String) uuid, r);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected void deleteResume(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    protected boolean checkExistResume(Object searchKey, String uuid) {
        if (!storage.containsKey(uuid)) throw new NotExistStorageException(uuid);
        return true;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
