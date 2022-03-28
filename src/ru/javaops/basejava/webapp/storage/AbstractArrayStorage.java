package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
    protected final void saveResume(Object index, Resume r) {
        final int indexResume = (int) index;

        //Проверка что в storage есть свободное место
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("The storage is full", r.getUuid());
        }

        //Вставка в массив
        if (indexResume >= -size - 1) {
            if (indexResume == -size - 1) { //Проверка на вставку в конец массива
                storage[size] = r;
            } else saveToArray(indexResume, r);
            size++;
        }
    }

    @Override
    protected final void updateResume(Object index, Resume r) {
        storage[(int) index] = r;
    }

    @Override
    protected final Resume getResume(Object index) {
        return storage[(int) index];
    }

    protected abstract void shiftItemsLeft(int indexResume);

    @Override
    protected final void deleteResume(Object index) {
        final int indexResume = (int) index;
        //Удаление из массива
        if (indexResume <= size - 1) {
            if (indexResume < size - 1) { //Проверка на удаление из конца массива
                shiftItemsLeft(indexResume); //смещение элемента(ов) массива влево на указанный индекс
            }
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected List<Resume> getListResumes() {
        return new LinkedList<>(Arrays.asList(Arrays.copyOf(storage,size)));
    }

    @Override
    public final int size() {
        return size;
    }
}
