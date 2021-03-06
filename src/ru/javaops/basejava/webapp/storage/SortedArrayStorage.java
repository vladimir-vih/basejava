package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected final Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Any name");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected final void saveToArray(int indexResume, Resume r) {
        final int insertIndex = -indexResume - 1;
        //смещение элементов массива вправо относительно индекса insertIndex
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        //запись нового Resume в индекс insertIndex
        storage[insertIndex] = r;
    }

    @Override
    protected final void shiftItemsLeft(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume, size - indexResume - 1);
    }
}
