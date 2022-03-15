package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int findIndex(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) return storage.indexOf(r);
        }
        return -1;
    }

    @Override
    protected void updateByIndex(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected final Resume getByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void saveOperation(int index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteByIndex(int index) {
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
