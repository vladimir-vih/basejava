package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapStorageSearchResume extends AbstractStorage{
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findSearchKey(String uuid) {
        Resume resume = storage.get(uuid);
        return resume == null ? -1 : resume ;
    }

    @Override
    protected void saveResume(Object oldResume, Resume newResume) {
        storage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void updateResume(Object oldResume, Resume newResume) {
        Resume resume = (Resume) oldResume;
        storage.replace(resume.getUuid(), resume, newResume);
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void deleteResume(Object oldResume) {
        Resume resume = (Resume) oldResume;
        storage.remove(resume.getUuid(), resume);
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
