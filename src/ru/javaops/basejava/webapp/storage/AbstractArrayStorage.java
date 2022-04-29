package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    private static final Logger log = Logger.getLogger(AbstractArrayStorage.class.getName());
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected boolean isExistSearchKey(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        log.info("Storage was cleared successfully");
    }

    protected abstract void saveToArray(int indexResume, Resume r);

    @Override
    protected final void saveResume(Integer index, Resume r) {
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
    protected final void updateResume(Integer index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected final Resume getResume(Integer index) {
        return storage[index];
    }

    protected abstract void shiftItemsLeft(int indexResume);

    @Override
    protected final void deleteResume(Integer index) {
        //Удаление из массива
        if (index <= size - 1) {
            if (index < size - 1) { //Проверка на удаление из конца массива
                shiftItemsLeft(index); //смещение элемента(ов) массива влево на указанный индекс
            }
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected List<Resume> getListResumes() {
        return new LinkedList<>(Arrays.asList(Arrays.copyOf(storage, size)));
    }

    @Override
    public final int size() {
        return size;
    }
}
