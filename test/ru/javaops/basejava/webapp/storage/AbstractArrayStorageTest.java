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

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume("UUID1"));
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
        Resume testResume = new Resume("UUID123");
        storage.save(testResume);
        assertEquals(4, storage.size());
        assertEquals(testResume, storage.get("UUID123"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume testResume = new Resume("UUID1");
        storage.save(testResume);
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
        Resume testResume = new Resume("UUID1");
        storage.update(testResume);
        assertEquals(3, storage.size());
        assertEquals(testResume, storage.get("UUID1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume testResume = new Resume("UUID5");
        storage.update(testResume);
    }

    @Test
    public void getExist() {
        Resume testResume = storage.get("UUID1");
        assertEquals(3, storage.size());
        assertEquals("UUID1", testResume.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        Resume testResume = storage.get("UUID5");
    }

    @Test
    public void deleteExist() {
        storage.delete("UUID1");
        try {
            storage.get("UUID1");
        } catch (NotExistStorageException e) {
            assertEquals(2, storage.size());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("UUID5");
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