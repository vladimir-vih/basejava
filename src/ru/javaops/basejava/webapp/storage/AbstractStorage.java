package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findUuidIndex(String uuid);

    protected abstract void saveOperation(Object uuidIndex, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final Object uuidIndex = findUuidIndex(uuid);
        try {
            checkExistResume(uuidIndex, uuid);
        } catch (NotExistStorageException e) {
            saveOperation(uuidIndex, r);
            System.out.println("Resume " + uuid + " was added");
            return;
        }
        throw new ExistStorageException(uuid);
    }

    protected abstract void updateByUuidIndex(Object uuidIndex, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Object uuidIndex= findUuidIndex(uuid);
        checkExistResume(uuidIndex, uuid);
        //Обновление резюме
        updateByUuidIndex(uuidIndex, resume);
        System.out.println("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getByUuidIndex(Object uuidIndex);

    @Override
    public final Resume get(String uuid) {
        final Object uuidIndex = findUuidIndex(uuid);
        checkExistResume(uuidIndex, uuid);
        //Возврат резюме
        return getByUuidIndex(uuidIndex);
    }

    protected abstract void deleteByUuidIndex(Object uuidIndex);

    @Override
    public final void delete(String uuid) {
        final Object uuidIndex = findUuidIndex(uuid);
        checkExistResume(uuidIndex, uuid);
        //Удаление из коллекции
        deleteByUuidIndex(uuidIndex);
        System.out.println("Resume " + uuid + " was deleted");
    }

    protected void checkExistResume(Object uuidIndex, String uuid) {
        if ((int) uuidIndex < 0) throw new NotExistStorageException(uuid);
    }
}
