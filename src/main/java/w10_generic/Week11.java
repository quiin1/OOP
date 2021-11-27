package w10_generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

public class Week11<T> {
    /**
     * sort generic.
     *
     * @param arr list arr.
     * @param <T> Integer or String.
     * @return sorted list.
     */
    public static <T> List<T> sortGeneric(List<T> arr) {
        Collections.sort(arr, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    return ((Integer) o1).compareTo((Integer) o2);
                }
                if (o1 instanceof String && o2 instanceof String) {
                    return ((String) o1).compareTo((String) o2);
                }
                if (o1 instanceof Person && o2 instanceof Person) {
                    return ((Person) o1).compareTo((Person) o2);
                }
                return 0;
            }
        });
        return arr;
    }

    /**
     * print.
     *
     * @param inputArray list arr.
     * @param <T>        Integer or String.
     */
    public static <T> void printArr(List<T> inputArray) {
        for (T element : inputArray) {
            System.out.printf("%s\n", element);
        }
    }

    /**
     * test client.
     *
     * @param args .
     */
    public static void main(String[] args) {
        Integer[] intArr1 = {10, 12, 3, -4, 5};
        Double[] douArr1 = {5.1, 2.5, 7.3, -4.5};
        String[] strArr1 = {"U", "E", "T", "V", "N", "U"};

        List<Integer> intArr = new ArrayList<>(asList(intArr1));
        List<Double> douArr = new ArrayList<>(asList(douArr1));
        List<String> strArr = new ArrayList<>(asList(strArr1));

        System.out.println("Mang intArr bao gom:");
        printArr(sortGeneric(intArr));

        System.out.println("\nMang strArr bao gom:");
        printArr(sortGeneric(strArr));
    }
}
