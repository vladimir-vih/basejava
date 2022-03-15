package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleared successfully");
    }

    protected abstract void saveToArray(int indexResume, Resume r);

    @Override
    protected final void saveOperation(int index, Resume r) {
        final String uuid = r.getUuid();

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("The storage is full", r.getUuid());
        }

        //Вставка в массив
        if (index >= -size - 1) {
            if (index == -size - 1) { //Проверка на вставку в конец массива
                storage[size] = r;
            } else saveToArray(index, r);
            size++;
        }
    }

    @Override
    protected final void updateByIndex(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected final Resume getByIndex(int index) {
        return storage[index];
    }

    protected abstract void shiftItemsLeft(int indexResume);

    @Override
    protected final void deleteByIndex(int index) {
        //Удаление из массива
        if (index <= size - 1) {
            if (index < size - 1) { //Проверка на удаление из конца массива
                shiftItemsLeft(index); //смещение элемента(ов) массива влево на указанный индекс
            }
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int size() {
        return size;
    }
}
