package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements ru.javaops.basejava.webapp.storage.Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleared successfully");
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveToArray(int indexResume, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("The storage is full", uuid);
        }

        final int indexResume = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume >= 0) {
            throw new ExistStorageException(uuid);
        }

        //Вставка в массив
        if (indexResume >= -size - 1) {
            if (indexResume == -size - 1) { //Проверка на вставку в конец массива
                storage[size] = r;
            } else saveToArray(indexResume, r);
            size++;
            System.out.println("Resume " + uuid + " was added");
        }
    }

    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final int indexResume= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume < 0) {
            throw new NotExistStorageException(uuid);
        }

        //Обновление резюме
        storage[indexResume] = resume;
        System.out.println("Resume " + uuid + " was updated.");
    }

    public final Resume get(String uuid) {
        final int indexResume= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume < 0) {
            throw new NotExistStorageException(uuid);
        }
        //Возврат резюме
        return storage[indexResume];
    }

    protected abstract void shiftItemsLeft(int indexResume);

    @Override
    public final void delete(String uuid) {
        final int indexResume = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume < 0) {
            throw new NotExistStorageException(uuid);
        }

        //Удаление из массива
        if (indexResume <= size - 1) {
            if (indexResume < size - 1) { //Проверка на удаление из конца массива
                shiftItemsLeft(indexResume); //смещение элемента(ов) массива влево на указанный индекс
            }
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " was deleted");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }
}
