package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected final int findIndex(String uuid) {
        for (int i = 0; i < size; ++i) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            System.out.println("Can't add " + uuid + " , the storage is full.");
            return;
        }

        //Проверка есть ли такое резюме чтобы не было дубликата uuid
        if (findIndex(uuid) >= 0) {
            System.out.println("Can't save " + uuid + ", already exists.");
            return;
        }

        //добавление нового резюме в storage c проверкой в IF есть ли такое резюме
        storage[size] = r;
        size++;
        System.out.println("Resume " + uuid + " was added");

    }

    @Override
    public final void delete(String uuid) {
        final int indexOfResume = findIndex(uuid);

        //Проверка есть ли такое резюме для удаления
        if (indexOfResume < 0) {
            System.out.println("Can't delete " + uuid + ", not found.");
            return;
        }

        //Удаление резюме
        storage[indexOfResume] = storage[size - 1];
        storage[size - 1] = null;
        size--;
        System.out.println(uuid + " removed.");
    }
}
