/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; ++i) storage[i] = null;
        size = 0;
        System.out.println("Успешно удаленны все данные из хранилища резюме");
    }

    void save(Resume r) {
        if (size < storage.length) {
            boolean isExistResume = false;

            //проверка есть ли такое резюме
            for (int i = 0; i < size; ++i) {
                if (r.uuid.equals(storage[i].uuid)) {
                    isExistResume = true;
                    System.out.println("Такое резюме уже существует.");
                    break;
                }
            }

            //добавление нового резюме в storage
            if (!isExistResume) {
                storage[size] = r;
                size++;
                System.out.println("Резюме успешно добавлено в хранилище");
            }
        } else System.out.println("Хранилище резюме заполнено, для добавления новог резюме необходим освободить место");
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; ++i) {
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

        for (int i = 0; i < size; ++i) {
            if (storage[i].uuid.equals(uuid)) {
                isFoundResume = true;
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
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
        if (size > 0) {
            Resume[] resumeExport = new Resume[size];

            for (int i = 0; i < size; ++i) {
                resumeExport[i] = storage[i];
            }

            return resumeExport;
        }
        System.out.println("Хранилище пустое. Нет данных для выгрузки.");
        return new Resume[0];
    }

    int size() {
        return size;
    }
}
