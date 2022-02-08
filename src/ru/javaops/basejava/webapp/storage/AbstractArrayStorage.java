package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleared successfully");
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveAction(int indexResume, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            System.out.println("Can't add " + uuid + " , the storage is full.");
            return;
        }

        final int indexResume = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume >= 0) {
            System.out.println("Can't save " + uuid + ", already exists.");
            return;
        }

        //Вставка в конец массива
        if (indexResume == -size - 1) {
            storage[size] = r;
            size++;
            System.out.println("Resume " + uuid + " was added");
            return;
        }

        //Вставка не в конец массива
        if (indexResume > -size - 1) {
            saveAction(indexResume, r);
            size++;
            System.out.println("Resume " + uuid + " was added");
        }
    }

    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final int findIndexResult= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult < 0) {
            System.out.println("Can't update " + uuid + " , doesn't exist");
            return;
        }

        //Обновление резюме
        storage[findIndexResult] = resume;
        System.out.println("Resume " + uuid + " was updated.");
    }

    public final Resume get(String uuid) {
        final int findIndexResult= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult < 0) {
            System.out.println("Can't get " + uuid + " , doesn't exist");
            return null;
        }
        //Возврат резюме
        return storage[findIndexResult];
    }

    protected abstract void deleteAction(int indexResume);

    @Override
    public final void delete(String uuid) {
        final int indexResume = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (indexResume < 0) {
            System.out.println("Can't delete " + uuid + " , doesn't exist");
            return;
        }

        //Удаление в конце массива
        if (indexResume == size - 1) {
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " was deleted");
            return;
        }

        //Удаление не в конце массива
        if (indexResume < size - 1) {
            deleteAction(indexResume);
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
