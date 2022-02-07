package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage was cleared successfully");
    }

    protected abstract int findIndex(String uuid);

    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final int findIndexResult= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult < 0) {
            System.out.println("Can't update " + uuid + " , doesn't exist");
            return;
        }

        //Обновление резюме
        storage[findIndexResult] = resume;
        System.out.println("Resume " + uuid + " was updated.");
    }

    public final Resume get(String uuid) {
        final int findIndexResult= findIndex(uuid);

        //Проверка есть ли такое резюме
        if (findIndexResult < 0) {
            System.out.println("Can't get " + uuid + " , doesn't exist");
            return null;
        }
        //Возврат резюме
        return storage[findIndexResult];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }
}
