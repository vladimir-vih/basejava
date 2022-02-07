package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected final int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            System.out.println("Can't add " + uuid + " , the storage is full.");
            return;
        }

        final int findIndexResult = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult >= 0) {
            System.out.println("Can't save " + uuid + ", already exists.");
            return;
        }

        //Вставка в конец массива
        if (findIndexResult == -size - 1) {
            storage[size] = r;
            size++;
            System.out.println("Resume " + uuid + " was added");
            return;
        }

        //Вставка не в конец массива
        if (findIndexResult > -size - 1) {
            final int insertIndex = -findIndexResult - 1;
            System.arraycopy(storage, insertIndex,storage,insertIndex + 1, size - insertIndex);
            storage[insertIndex] = r;
            size++;
            System.out.println("Resume " + uuid + " was added");
        }
    }

    @Override
    public final void delete(String uuid) {
        final int findIndexResult = findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult < 0) {
            System.out.println("Can't delete " + uuid + " , doesn't exist");
            return;
        }

        //Удаление в конце массива
        if (findIndexResult == size - 1) {
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " was deleted");
            return;
        }

        //Удаление не в конце массива
        if (findIndexResult < size - 1) {
            System.arraycopy(storage, findIndexResult + 1, storage, findIndexResult, size - findIndexResult);
            storage[size - 1] = null;
            size--;
            System.out.println("Resume " + uuid + " was deleted");
        }

    }
}
