package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int findIndex(String uuid);

    protected abstract void saveOperation(int index, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final int index = findIndex(uuid);
        if (index >= 0) throw new ExistStorageException(uuid);
        saveOperation(index, r);
        System.out.println("Resume " + uuid + " was added");
    }

    protected abstract void updateByIndex(int index, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final int index= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        //Обновление резюме
        updateByIndex(index, resume);
        System.out.println("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getByIndexOrUuid(int index, String uuid);

    @Override
    public final Resume get(String uuid) {
        final int index= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        //Возврат резюме
        return getByIndexOrUuid(index, uuid);
    }

    protected abstract void deleteByIndexOrUuid(int index, String uuid);

    @Override
    public final void delete(String uuid) {
        final int index = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (index < 0) { throw new NotExistStorageException(uuid); }

        //Удаление из коллекции
        deleteByIndexOrUuid(index, uuid);
        System.out.println("Resume " + uuid + " was deleted");
    }
}
