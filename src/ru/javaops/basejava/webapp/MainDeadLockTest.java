package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.ContactType;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.Section;
import ru.javaops.basejava.webapp.model.SectionType;
import ru.javaops.basejava.webapp.storage.ListStorage;
import ru.javaops.basejava.webapp.storage.PathStorage;
import ru.javaops.basejava.webapp.storage.Storage;
import ru.javaops.basejava.webapp.storage.serializestrategy.JsonSerializer;

import java.util.Map;
import java.util.UUID;

public class MainDeadLockTest {
    /*
    This code demonstrate the deadlock of two Threads which are synchronised by the two objects.
    Both threads use the same two objects for the "Lock" operation, but do it in different sequence.

    Prerequisites:
        - main storage is ListStorage
        - main storage has some resumes with the corrupted data in the "Full Name" field
        - there is intermediate PathStorage where corrupted resumes are moved, fixed
        and transferred back to the ListStorage

    Thread1 find corrupted resumes in the ListStorage and moves it to the PathStorage.
    Thread1 makes "Lock" for the ListStorage first and then for the PastStorage

    Thread2 fix corrupted resumes in the PathStorage and moves it back to the ListStorage.
    Thread2 makes "Lock" for the PathStorage first and then for the ListStorage
     */
    public static final int RESUME_AMOUNT = 100;

    public static void main(String[] args) {
        final Storage tempStorage = new PathStorage("C:\\Vova\\git\\javaops\\basejava\\src\\ru\\javaops\\basejava\\webapp" +
                "\\storage\\files", new JsonSerializer());
        final Storage mainStorage = new ListStorage();

        for (int i = 0; i < RESUME_AMOUNT; i++) {
            if (i % 2 == 0) {
                mainStorage.save(ResumeTestData.getInstance(UUID.randomUUID().toString(), "FULL NAME " + i));
            } else {
                mainStorage.save(ResumeTestData.getInstance(UUID.randomUUID().toString(), "CORRUPTED " + i));
            }
        }
        tempStorage.clear();

        Thread thread1 = new Thread(new CorruptedResumeFinder(mainStorage, tempStorage));
        Thread thread2 = new Thread(new CorruptedResumeFixer(mainStorage, tempStorage));

        thread1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Job completed");
    }

}

class CorruptedResumeFinder implements Runnable {
    private final Storage mainStorage;
    private final Storage tempStorage;

    CorruptedResumeFinder(Storage mainStorage, Storage tempStorage) {
        this.mainStorage = mainStorage;
        this.tempStorage = tempStorage;
    }

    @Override
    public void run() {
        System.out.println("Finder thread started");
        synchronized (mainStorage) {
            System.out.println("Finder Locked mainStorage");
            for (Resume resume : mainStorage.getAllSorted()) {
                if (resume.getFullName().contains("CORRUPTED")) {
                    synchronized (tempStorage) {
                        System.out.println("Finder Locked tempStorage");
                        tempStorage.save(resume);
                    }
                    System.out.println("Finder UNLocked tempStorage");
                    mainStorage.delete(resume.getUuid());
                }
            }
        }
        System.out.println("Finder UNLocked mainStorage");
    }
}

class CorruptedResumeFixer implements Runnable {
    private final Storage mainStorage;
    private final Storage tempStorage;

    public CorruptedResumeFixer(Storage mainStorage, Storage tempStorage) {
        this.mainStorage = mainStorage;
        this.tempStorage = tempStorage;
    }

    @Override
    public void run() {
        System.out.println("Fixer thread started");
        synchronized (tempStorage) {
            System.out.println("Fixer Locked tempStorage");
            while (tempStorage.size() > 0) {
                for (Resume resume : tempStorage.getAllSorted()) {
                    final String uuid = resume.getUuid();
                    String fullname = resume.getFullName();
                    final Map<ContactType, String> contacts = resume.getContacts();
                    final Map<SectionType, Section> sections = resume.getSections();
                    fullname = fullname.replace("CORRUPTED", "FULL NAME ");
                    synchronized (mainStorage) {
                        System.out.println("Fixer Locked mainStorage");
                        mainStorage.save(new Resume(uuid, fullname, contacts, sections));
                    }
                    System.out.println("Fixer UNLocked mainStorage");
                    tempStorage.delete(uuid);
                }
            }
        }
        System.out.println("Fixer UNLocked tempStorage");
    }
}