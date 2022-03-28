package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.SortedArrayStorage;
import ru.javaops.basejava.webapp.storage.Storage;

/**
 * Test for your ru.javaops.basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String FULL_NAME_1 = "FULLNAME1";
    private static final String FULL_NAME_2 = "FULLNAME2";
    private static final String FULL_NAME_3 = "FULLNAME3";
    private static final String FULL_NAME_4 = "FULLNAME4";

    public static void main(String[] args) {
        Resume r1 = new Resume(UUID_1, FULL_NAME_1);
        Resume r2 = new Resume(UUID_2, FULL_NAME_2);
        Resume r3 = new Resume(UUID_3, FULL_NAME_3);

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
        Resume r2New = new Resume(UUID_2, FULL_NAME_2);
        ARRAY_STORAGE.update(r2New);
        printAll();

        System.out.println("Try to update uuid4");
        Resume r4 = new Resume(UUID_4, FULL_NAME_4);
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
