package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract Object findSearchKey(String uuid);

    protected abstract void saveResume(Object searchKey, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final Object searchKey = findSearchKey(uuid);
        try {
            checkExistResume(searchKey, uuid);
        } catch (NotExistStorageException e) {
            saveResume(searchKey, r);
            System.out.println("Resume " + uuid + " was added");
            return;
        }
        throw new ExistStorageException(uuid);
    }

    protected abstract void updateResume(Object searchKey, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey= findSearchKey(uuid);
        checkExistResume(searchKey, uuid);
        //Обновление резюме
        updateResume(searchKey, resume);
        System.out.println("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getResume(Object searchKey);

    @Override
    public final Resume get(String uuid) {
        final Object searchKey = findSearchKey(uuid);
        checkExistResume(searchKey, uuid);
        //Возврат резюме
        return getResume(searchKey);
    }

    protected abstract void deleteResume(Object searchKey);

    @Override
    public final void delete(String uuid) {
        final Object searchKey = findSearchKey(uuid);
        checkExistResume(searchKey, uuid);
        //Удаление из коллекции
        deleteResume(searchKey);
        System.out.println("Resume " + uuid + " was deleted");
    }

    protected boolean checkExistResume(Object searchKey, String uuid) {
        if ((int) searchKey < 0) throw new NotExistStorageException(uuid);
        return true;
    }
}
