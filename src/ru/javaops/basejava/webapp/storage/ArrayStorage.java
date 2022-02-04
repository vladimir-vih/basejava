package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;
    private final int INDEX_NOT_FOUND = -1;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleared successfully");
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; ++i) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        //Сохранение нового резюме, с проверкой что в storage есть свободное место
        if (size < storage.length) {
            //добавление нового резюме в storage c проверкой в IF есть ли такое резюме
            int indexOfResume = findIndex(uuid);
            if (indexOfResume == INDEX_NOT_FOUND) {
                storage[size] = r;
                size++;
                System.out.println("Resume " + uuid + " was added");
            } else System.out.println("Can't save " + uuid + ", already exists.");
        } else System.out.println("Can't add " + uuid + " , the storage is full.");
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int indexOfResume = findIndex(uuid);
        if (indexOfResume != INDEX_NOT_FOUND) {
            storage[indexOfResume] = resume;
            System.out.println("\nResume " + uuid + " was updated.");
        } else System.out.println("Can't update " + uuid + " , doesn't exist");
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);

        if (indexOfResume != INDEX_NOT_FOUND) {
            return storage[indexOfResume];
        }
        System.out.println("Resume " + uuid + " not found.");
        /*
        вероятно нужно выбрасывать/ловить какой-то exception,
        но данная тема еще не затрагивалась в обучении, поэтому временно ставлю возврат null
        */
        return null;
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);

        if (indexOfResume != INDEX_NOT_FOUND) {
            storage[indexOfResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println(uuid + " removed.");
        } else System.out.println("Can't delete " + uuid + ", not found.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
