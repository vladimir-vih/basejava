/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; ++i) storage[i] = null;
        System.out.println("Успешно удаленны все данные из хранилища резюме");
    }

    void save(Resume r) {
        if (this.size() < storage.length) {
            boolean isExistResume = false;

            //проверка есть ли такое резюме
            for (int i = 0; i < this.size(); ++i) {
                if (r.uuid.equals(storage[i].uuid)) {
                    isExistResume = true;
                    System.out.println("Такое резюме уже существует.");
                    break;
                }
            }

            //добавление нового резюме в storage
            if (!isExistResume) {
                storage[this.size()] = r;
                System.out.println("Резюме успешно добавлено в хранилище");
            }
        } else System.out.println("Хранилище резюме заполнено, для добавления новог резюме необходим освободить место");
    }

    Resume get(String uuid) {
        for (int i = 0; i < this.size(); ++i) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        System.out.println("Такое резюме не найдено");
        /*
        вероятно нужно выбрасывать/ловить какой-то exception,
        но данная тема еще не затрагивалась в обучении, поэтому временно ставлю возврат null
        */
        return null;
    }

    void delete(String uuid) {
        boolean isFoundResume = false;

        for (int i = 0; i < this.size(); ++i) {
            if (storage[i].uuid.equals(uuid)) {
                isFoundResume = true;
                storage[i] = storage[this.size() - 1];
                storage[this.size() - 1] = null;
                System.out.println("Резюме успешно удалено из хранилища.");
                break;
            }
        }

        if (!isFoundResume) System.out.println("Такое резюме не найдено в хранилище.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (this.size() > 0) {
            Resume[] resumeExport = new Resume[this.size()];

            for (int i = 0; i < this.size(); ++i) {
                resumeExport[i] = storage[i];
            }

            return resumeExport;
        }
        System.out.println("Хранилище пустое. Нет данных для выгрузки.");
        return new Resume[0];
    }

    int size() {
        int size = 0;

        for (int i = 0; i < storage.length; ++i) {
            if (storage[i] == null) break;
            size++;
        }
        return size;
    }
}
