package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected final Map<String, Integer> findUuidIndex(String uuid) {
        Map<String, Integer> uuidIndex = new HashMap<>();
        for (int i = 0; i < size; ++i) {
            if (storage[i].getUuid().equals(uuid)) {
                uuidIndex.put(uuid, i);
                return uuidIndex;
            }
        }
        uuidIndex.put(uuid, -1);
        return uuidIndex;
    }

    @Override
    protected final void saveToArray(int indexResume, Resume r) {
        storage[size] = r;
    }

    @Override
    protected final void shiftItemsLeft(int indexResume) {
        storage[indexResume] = storage[size - 1];
    }
}
