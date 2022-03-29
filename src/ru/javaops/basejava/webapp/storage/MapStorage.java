package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isExistSearchKey(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Object uuid, Resume r) {
        storage.put(r.getUuid(), r);
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
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getListResumes() {
        return new LinkedList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
