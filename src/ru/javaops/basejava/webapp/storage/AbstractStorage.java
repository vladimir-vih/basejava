package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Map;

public abstract class AbstractStorage implements Storage {
    protected abstract Map<String, Integer> findUuidIndex(String uuid);

    protected abstract void saveOperation(int index, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final int index = findUuidIndex(uuid).get(uuid);
        if (index >= 0) throw new ExistStorageException(uuid);
        saveOperation(index, r);
        System.out.println("Resume " + uuid + " was added");
    }

    protected abstract void updateByUuidIndex(int index, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Map<String, Integer> uuidIndex = findUuidIndex(uuid);
        final int index= uuidIndex.get(uuid);
        checkExistResume(uuidIndex);
        //Обновление резюме
        updateByUuidIndex(index, resume);
        System.out.println("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getByUuidIndex(Map<String, Integer> uuidIndex);

    @Override
    public final Resume get(String uuid) {
        final Map<String, Integer> uuidIndex = findUuidIndex(uuid);
        checkExistResume(uuidIndex);
        //Возврат резюме
        return getByUuidIndex(uuidIndex);
    }

    protected abstract void deleteByUuidIndex(Map<String, Integer> uuidIndex);

    @Override
    public final void delete(String uuid) {
        final Map<String, Integer> uuidIndex = findUuidIndex(uuid);
        checkExistResume(uuidIndex);
        //Удаление из коллекции
        deleteByUuidIndex(uuidIndex);
        System.out.println("Resume " + uuid + " was deleted");
    }

    private void checkExistResume(Map<String, Integer> uuidIndex) {
        String uuid = uuidIndex.keySet().toArray(new String[0])[0];
        if (uuidIndex.get(uuid) < 0) { throw new NotExistStorageException(uuid); }
    }
}
