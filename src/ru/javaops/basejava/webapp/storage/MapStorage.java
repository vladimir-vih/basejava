package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Map<String, Integer> findUuidIndex(String uuid) {
        Map<String, Integer> uuidIndex = new HashMap<>();
        if (storage.containsKey(uuid)) {
            uuidIndex.put(uuid, 0);
            return uuidIndex;
        }
        uuidIndex.put(uuid, -1);
        return uuidIndex;
    }

    @Override
    protected void saveOperation(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateByUuidIndex(int index, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getByUuidIndex(Map<String, Integer> uuidIndex) {
        final String uuid = uuidIndex.keySet().toArray(new String[0])[0];
        return storage.get(uuid);
    }

    @Override
    protected void deleteByUuidIndex(Map<String, Integer> uuidIndex) {
        final String uuid = uuidIndex.keySet().toArray(new String[0])[0];
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
