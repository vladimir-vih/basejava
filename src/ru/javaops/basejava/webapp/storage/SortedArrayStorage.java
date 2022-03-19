package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected final Map<String, Integer> findUuidIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Integer index = Arrays.binarySearch(storage, 0, size, searchKey);
        Map<String, Integer> uuidIndex = new HashMap<>();
        uuidIndex.put(uuid, index);
        return uuidIndex;
    }

    @Override
    protected final void saveToArray(int indexResume, Resume r) {
        final int insertIndex = -indexResume - 1;
        //смещение элементов массива вправо относительно индекса insertIndex
        System.arraycopy(storage, insertIndex,storage,insertIndex + 1, size - insertIndex);
        //запись нового Resume в индекс insertIndex
        storage[insertIndex] = r;
    }

    @Override
    protected final void shiftItemsLeft(int indexResume) {
        System.arraycopy(storage, indexResume + 1, storage, indexResume, size - indexResume - 1);
    }
}
