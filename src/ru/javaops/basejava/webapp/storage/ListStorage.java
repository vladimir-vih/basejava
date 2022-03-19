package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Map<String, Integer> findUuidIndex(String uuid) {
        Map<String, Integer> uuidIndex = new HashMap<>();
        for (int i = 0; i < storage.size(); i++) {
            Resume tempResume = storage.get(i);
            if (uuid.equals(tempResume.getUuid())) {
                uuidIndex.put(uuid, i);
                return uuidIndex;
            }
        }
        uuidIndex.put(uuid, -1);
        return uuidIndex;
    }

    @Override
    protected void updateByUuidIndex(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected final Resume getByUuidIndex(Map<String, Integer> uuidIndex) {
        final int index = uuidIndex.values().toArray(new Integer[0])[0];
        return storage.get(index);
    }

    @Override
    protected void saveOperation(int index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteByUuidIndex(Map<String, Integer> uuidIndex) {
        final int index = uuidIndex.values().toArray(new Integer[0])[0];
        storage.remove(index);
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
