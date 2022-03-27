package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.SortedArrayStorage;
import ru.javaops.basejava.webapp.storage.Storage;

/**
 * Test for your ru.javaops.basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        //Test for the Update method
        System.out.println("\n======\nUpdate method test.");
        Resume r2New = new Resume();
        r2New.setUuid("uuid2");
        ARRAY_STORAGE.update(r2New);
        printAll();

        System.out.println("Try to update uuid4");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        ARRAY_STORAGE.update(r4);
        printAll();
        System.out.println("The end of update method test\n======");
        //End of test for the update method

        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
