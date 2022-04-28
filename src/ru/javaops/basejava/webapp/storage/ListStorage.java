package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume tempResume = storage.get(i);
            if (uuid.equals(tempResume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExistSearchKey(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void updateResume(Integer index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected final Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void saveResume(Integer index, Resume r) {
        storage.add(r);
    }

    @Override
    protected void deleteResume(Integer index) {
        storage.remove((int) index);
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
