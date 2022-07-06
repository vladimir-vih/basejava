package ru.javaops.basejava.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.ResumeTestData;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.getInstance().getStorageDir();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_1 = "FULLNAME1";
    private static final String FULL_NAME_2 = "FULLNAME2";
    private static final String FULL_NAME_3 = "FULLNAME3";
    private static final String FULL_NAME_4 = "FULLNAME4";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.getInstance(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.getInstance(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.getInstance(UUID_3, FULL_NAME_3);
        RESUME_4 = ResumeTestData.getInstance(UUID_4, FULL_NAME_4);

//        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
//        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
//        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
//        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    }

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    /*@After
    public void tearDown() {
        storage.clear();
    }*/

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void saveNotExist() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void updateExist() {
        storage.update(RESUME_1);
        assertSize(3);
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void getExist() {
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void deleteExist() {
        storage.delete(UUID_1);
        try {
            storage.get(UUID_1);
            fail("Error! Get operation didn't throw exception during the test!");
        } catch (NotExistStorageException e) {
            assertSize(2);
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = new LinkedList<>();
        expectedResumes.add(RESUME_1);
        expectedResumes.add(RESUME_2);
        expectedResumes.add(RESUME_3);
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}