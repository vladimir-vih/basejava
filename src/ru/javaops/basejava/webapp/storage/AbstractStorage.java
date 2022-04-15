package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage implements Storage {
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    /**
    findSearchKey method
    @return negative int key if "uuid" is not found, otherwise it can return any Object type with the key.
    */

    protected abstract Object findSearchKey(String uuid);

    protected abstract void saveResume(Object searchKey, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final Object searchKey = receiveNotExistedSearchKey(uuid);
        saveResume(searchKey, r);
        log.info("Resume " + uuid + " was added");
    }

    protected abstract void updateResume(Object searchKey, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final Object searchKey= receiveExistedSearchKey(uuid);
        //Обновление резюме
        updateResume(searchKey, resume);
        log.info("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getResume(Object searchKey);

    @Override
    public final Resume get(String uuid) {
        log.info("Get resume" + uuid);
        final Object searchKey = receiveExistedSearchKey(uuid);
        //Возврат резюме
        return getResume(searchKey);
    }

    protected abstract void deleteResume(Object searchKey);

    @Override
    public final void delete(String uuid) {
        final Object searchKey = receiveExistedSearchKey(uuid);
        //Удаление из коллекции
        deleteResume(searchKey);
        log.info("Resume " + uuid + " was deleted");
    }

    protected abstract boolean isExistSearchKey(Object searchKey);

    private Object receiveExistedSearchKey(String uuid) {
        final Object searchKey = findSearchKey(uuid);
        if (isExistSearchKey(searchKey)) return searchKey;
        log.warning("Resume " + uuid + ", doesn't exist.");
        throw new NotExistStorageException(uuid);
    }

    private Object receiveNotExistedSearchKey(String uuid) {
        final Object searchKey = findSearchKey(uuid);
        if (isExistSearchKey(searchKey)) {
            log.warning("Resume " + uuid + ", already exists.");
            throw new ExistStorageException(uuid);
        }
        return  searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        log.info("Get all sorted");
        List<Resume> resultList = getListResumes();
        resultList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resultList;
    }

    protected abstract List<Resume> getListResumes();
}
