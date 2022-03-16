package ru.javaops.basejava.webapp.storage;

import org.junit.Test;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MapStorageTest extends AbstractStorageTest{
    public MapStorageTest(){super(new MapStorage());}

    @Test
    @Override
    public void getAll(){
        Resume[] testArrayResume = {storage.get("uuid1"), storage.get("uuid2"), storage.get("uuid3")};
        Resume[] getAllArray = storage.getAll();
        assertEquals(3, getAllArray.length);
        List<Resume> getAllList = Arrays.asList(getAllArray);
        boolean checkResult = true;
        int i = 0;
        while (checkResult && i < testArrayResume.length) {
            if (!getAllList.contains(testArrayResume[i])) checkResult = false;
            i++;
        }
        assertTrue(checkResult);
    }


}