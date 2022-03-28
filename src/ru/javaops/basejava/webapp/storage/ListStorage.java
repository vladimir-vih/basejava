package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume tempResume = storage.get(i);
            if (uuid.equals(tempResume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Object index, Resume r) {
        storage.set((int) index, r);
    }

    @Override
    protected final Resume getResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void saveResume(Object index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteResume(Object index) {
        final int indexResume = (int) index;
        storage.remove(indexResume);
    }

    @Override
    protected List<Resume> getListResumes() {
        return new LinkedList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
