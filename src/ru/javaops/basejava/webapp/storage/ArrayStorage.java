package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;
    private final int INXDEX_NOT_FOUND = -1;

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
        //System.out.println("Index was not found, because resume " + resume.getUuid() + " doesn't exist at the storage ");
        return INXDEX_NOT_FOUND;
    }

    public void save(Resume r) {
        //Сохранение нового резюме, с проверкой что в storage есть свободное место
        if (size < storage.length) {
            //добавление нового резюме в storage c проверкой в IF есть ли такое резюме
            int indexOfResume = findIndex(r.getUuid());
            if (indexOfResume == INXDEX_NOT_FOUND) {
                storage[size] = r;
                size++;
                System.out.println("Resume " + r.getUuid() + " was added to the storage successfully");
            } else System.out.println("Resume " + r.getUuid() + " wasn't saved, because it already exists at the storage.");
        } else System.out.println("The Resume " + r.getUuid() + " can't be added, because the storage is full.");
    }

    public void update(Resume resume) {
        int indexOfResume = findIndex(resume.getUuid());
        if (indexOfResume != INXDEX_NOT_FOUND) {
            storage[indexOfResume] = resume;
            System.out.println("\nResume " + resume.getUuid() + " was updated successfully.");
        } else System.out.println("Update for the Resume " + resume.getUuid() + " wasn't completed, because resume doesn't exist at the storage");
    }

    public Resume get(String uuid) {
        int indexOfResume = findIndex(uuid);

        if (indexOfResume != INXDEX_NOT_FOUND) {
            return storage[indexOfResume];
        }
        System.out.println("Resume " + uuid + " was not found at the storage.");
        /*
        вероятно нужно выбрасывать/ловить какой-то exception,
        но данная тема еще не затрагивалась в обучении, поэтому временно ставлю возврат null
        */
        return null;
    }

    public void delete(String uuid) {
        int indexOfResume = findIndex(uuid);

        if (indexOfResume != INXDEX_NOT_FOUND) {
            storage[indexOfResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println("The resume " + uuid + " was successfully removed from the storage.");
        } else System.out.println("The resume " + uuid + " was not deleted from the storage, because it was not found at the storage.");
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
