package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapStorageSearchResume extends AbstractStorage<Resume>{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExistSearchKey(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void saveResume(Resume oldResume, Resume newResume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void updateResume(Resume oldResume, Resume newResume) {
        storage.replace(oldResume.getUuid(), oldResume, newResume);
    }

    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected void deleteResume(Resume oldResume) {
        storage.remove(oldResume.getUuid(), oldResume);
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
