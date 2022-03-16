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
        for (int i = 0; i < storage.size(); i++) {
            Resume tempResume = storage.get(i);
            if (uuid.equals(tempResume.getUuid())) return i;
        }
        return -1;
    }

    @Override
    protected void updateByIndex(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected final Resume getByIndexOrUuid(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected void saveOperation(int index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteByIndexOrUuid(int index, String uuid) {
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
