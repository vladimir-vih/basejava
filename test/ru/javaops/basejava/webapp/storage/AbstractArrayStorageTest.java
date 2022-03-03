package ru.javaops.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

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
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void saveNotExist() {
        Resume testResume = new Resume("UUID123");
        storage.save(testResume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(testResume, storage.get("UUID123"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume testResume = new Resume("UUID1");
        storage.save(testResume);
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(testResume, storage.get("UUID1"));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws IllegalAccessException, NoSuchFieldException {
        Field storageLimit = storage.getClass().getSuperclass().getDeclaredField("STORAGE_LIMIT");
        for(int i = 3; i < (int) storageLimit.get(storage); i++) {
            storage.save(new Resume());
        }
        storage.save(new Resume());
    }

    @Test
    public void updateExist() {
        Resume testResume = new Resume("UUID1");
        storage.update(testResume);
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(testResume, storage.get("UUID1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume testResume = new Resume("UUID5");
        storage.update(testResume);
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(testResume, storage.get("UUID5"));
    }

    @Test
    public void getExist() {
        Resume testResume = storage.get("UUID1");
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals("UUID1", testResume.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        Resume testResume = storage.get("UUID5");
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals("UUID5", testResume.getUuid());
    }

    @Test
    public void deleteExist() {
        storage.delete("UUID1");
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("UUID5");
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] arrayResume = storage.getAll();
        Assert.assertEquals(storage.size(), arrayResume.length);
        storage.clear();
        arrayResume = storage.getAll();
        Assert.assertEquals(storage.size(), arrayResume.length);
    }

    @Test
    public void size() {
        int size = storage.size();
        Assert.assertEquals(3, size);
    }
}