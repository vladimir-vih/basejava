package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int findIndex(String uuid) {
        if (storage.containsKey(uuid)) return 0;
        return -1;
    }

    @Override
    protected void saveOperation(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateByIndex(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getByIndexOrUuid(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteByIndexOrUuid(int index, String uuid) {
        storage.remove(uuid);
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
