package ru.javaops.basejava.webapp.storage;

import org.junit.Test;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import static org.junit.Assert.fail;
import static ru.javaops.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("Any name"));
            }
        }
        catch (StorageException e) {
            fail("Storage exception occurs too early");
        }
        storage.save(new Resume("Any name"));
    }
}