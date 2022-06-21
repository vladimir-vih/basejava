package ru.javaops.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class MainStreamApiTask {
    private static final int ARRAY_SIZE = 5;
    private static final int[] values = new int[ARRAY_SIZE];
    private static final List<Integer> integers = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            int random = ThreadLocalRandom.current().nextInt(1, 10);
            values[i] = random;
            integers.add(random);
        }
        System.out.println("Original array: " + Arrays.toString(values));
        System.out.println("Minimum number from the array digits: " + minValue(values));

        System.out.println("\nOriginal list: " + integers);
        System.out.println("Original list sum: " + integers.stream().mapToInt(Integer::intValue).sum());
        System.out.println("Result list: " + oddOrEven(integers));
    }

    /*
     * Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число, с
     * оставленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
     * Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
     * */
    private static int minValue(int[] values) {
        AtomicReference<Integer> i = new AtomicReference<>(1);
        return Arrays.stream(values)
                .boxed()
                .distinct()
                .sorted((Integer x, Integer y) -> Integer.compare(y, x))
                .reduce(0,(x, y) -> x + y * i.getAndUpdate(z -> z * 10));
    }

    /*
    * реализовать метод List<Integer> oddOrEven(List<Integer> integers)
    * если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
    * Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
    * */
    private static List<Integer> oddOrEven(List<Integer> integers){
        final List<Integer> oddList = new ArrayList<>();
        final List<Integer> evenList = new ArrayList<>();

        int sum = integers.stream().mapToInt(Integer::intValue)
                .peek(x -> {
                    if (x % 2 == 0) {
                        oddList.add(x);
                    } else evenList.add(x);
                }).sum();
        return sum % 2 == 0 ? oddList : evenList;
    }
}
