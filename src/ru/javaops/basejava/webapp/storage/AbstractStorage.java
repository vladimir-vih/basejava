package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    /**
    findSearchKey method
    @return negative int key if "uuid" is not found, otherwise it can return any Object type with the key.
    */

    protected abstract SK findSearchKey(String uuid);

    protected abstract void saveResume(SK searchKey, Resume r);

    @Override
    public final void save(Resume r) {
        final String uuid = r.getUuid();
        final SK searchKey = receiveNotExistedSearchKey(uuid);
        saveResume(searchKey, r);
        log.info("Resume " + uuid + " was added");
    }

    protected abstract void updateResume(SK searchKey, Resume r);

    @Override
    public final void update(Resume resume) {
        final String uuid = resume.getUuid();
        final SK searchKey= receiveExistedSearchKey(uuid);
        //Обновление резюме
        updateResume(searchKey, resume);
        log.info("Resume " + uuid + " was updated.");
    }

    protected abstract Resume getResume(SK searchKey);

    @Override
    public final Resume get(String uuid) {
        log.info("Get resume" + uuid);
        final SK searchKey = receiveExistedSearchKey(uuid);
        //Возврат резюме
        return getResume(searchKey);
    }

    protected abstract void deleteResume(SK searchKey);

    @Override
    public final void delete(String uuid) {
        final SK searchKey = receiveExistedSearchKey(uuid);
        //Удаление из коллекции
        deleteResume(searchKey);
        log.info("Resume " + uuid + " was deleted");
    }

    protected abstract boolean isExistSearchKey(SK searchKey);

    private SK receiveExistedSearchKey(String uuid) {
        final SK searchKey = findSearchKey(uuid);
        if (isExistSearchKey(searchKey)) return searchKey;
        log.warning("Resume " + uuid + ", doesn't exist.");
        throw new NotExistStorageException(uuid);
    }

    private SK receiveNotExistedSearchKey(String uuid) {
        final SK searchKey = findSearchKey(uuid);
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
