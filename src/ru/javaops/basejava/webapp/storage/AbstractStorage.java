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

    protected abstract Resume getByIndex(int index);

    @Override
    public final Resume get(String uuid) {
        final int index= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        //Возврат резюме
        return getByIndex(index);
    }

    protected abstract void deleteByIndex(int index);

    @Override
    public final void delete(String uuid) {
        final int index = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (index < 0) { throw new NotExistStorageException(uuid); }

        //Удаление из коллекции
        deleteByIndex(index);
        System.out.println("Resume " + uuid + " was deleted");
    }
}
