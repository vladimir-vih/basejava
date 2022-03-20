package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object findUuidIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume tempResume = storage.get(i);
            if (uuid.equals(tempResume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateByUuidIndex(Object uuidIndex, Resume r) {
        storage.set((int) uuidIndex, r);
    }

    @Override
    protected final Resume getByUuidIndex(Object uuidIndex) {
        return storage.get((int) uuidIndex);
    }

    @Override
    protected void saveOperation(Object uuidIndex, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteByUuidIndex(Object uuidIndex) {
        final int index = (int) uuidIndex;
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
