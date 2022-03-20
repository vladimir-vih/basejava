package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findUuidIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void saveOperation(Object uuidIndex, Resume r) {
        storage.put((String) uuidIndex, r);
    }

    @Override
    protected void updateByUuidIndex(Object uuidIndex, Resume r) {
        storage.put((String) uuidIndex, r);
    }

    @Override
    protected Resume getByUuidIndex(Object uuidIndex) {
        return storage.get((String) uuidIndex);
    }

    @Override
    protected void deleteByUuidIndex(Object uuidIndex) {
        storage.remove((String) uuidIndex);
    }

    @Override
    protected void checkExistResume(Object uuidIndex, String uuid) {
        if (!storage.containsKey(uuid)) throw new NotExistStorageException(uuid);
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
