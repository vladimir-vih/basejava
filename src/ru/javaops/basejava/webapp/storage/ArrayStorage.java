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
    protected void saveToArray(int indexResume, Resume r) {
        storage[size] = r;
    }

    @Override
    protected void shiftItemsLeft(int indexResume) {
        storage[indexResume] = storage[size - 1];
    }
}
