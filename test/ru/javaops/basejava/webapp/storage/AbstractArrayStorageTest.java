package ru.javaops.basejava.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import static org.junit.Assert.*;
import static ru.javaops.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    protected Resume resumeExist = new Resume("UUID1");
    protected Resume resumeNotExist = new Resume("UUID5");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resumeExist);
        storage.save(new Resume("UUID2"));
        storage.save(new Resume("UUID3"));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void saveNotExist() {
        storage.save(resumeNotExist);
        assertEquals(4, storage.size());
        assertEquals(resumeNotExist, storage.get(resumeNotExist.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resumeExist);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            fail("Storage overflow occurs too early");
        }
        storage.save(new Resume());
    }

    @Test
    public void updateExist() {
        storage.update(resumeExist);
        assertEquals(3, storage.size());
        assertEquals(resumeExist, storage.get(resumeExist.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resumeNotExist);
    }

    @Test
    public void getExist() {
        assertEquals(resumeExist, storage.get(resumeExist.getUuid()));
        assertEquals(3, storage.size());

    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(resumeNotExist.getUuid());
    }

    @Test
    public void deleteExist() {
        storage.delete(resumeExist.getUuid());
        try {
            storage.get(resumeExist.getUuid());
            fail("Error! Get operation didn't throw exception during the test!");
        } catch (NotExistStorageException e) {
            assertEquals(2, storage.size());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(resumeNotExist.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] testArrayResume = {storage.get("UUID1"), storage.get("UUID2"), storage.get("UUID3")};
        assertArrayEquals(testArrayResume, storage.getAll());
    }

    @Test
    public void size() {
        int size = storage.size();
        assertEquals(3, size);
    }
}