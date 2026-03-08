package homework;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamTasks {

    // Task 1: Return string like "1. Ivan, 3. Peter" for odd-indexed names
    public static String oddIndexedNames(List<String> names) {
        return java.util.stream.IntStream.range(0, names.size())
                .filter(i -> i % 2 != 0)
                .mapToObj(i -> i + ". " + names.get(i))
                .collect(Collectors.joining(", "));
    }

    // Task 2: Uppercase and sort descending (Z to A)
    public static List<String> upperCaseSortedDesc(List<String> strings) {
        return strings.stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    // Task 3: Extract all numbers from string array, sort and join
    public static String sortedNumbers(String[] arr) {
        return Arrays.stream(arr)
                .flatMap(s -> Arrays.stream(s.split(",\\s*")))
                .map(Integer::parseInt)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    // Task 4: Linear congruential generator using Stream.iterate
    public static Stream<Long> lcg(long a, long c, long m) {
        long seed = 0;
        return Stream.iterate(seed, x -> (a * x + c) % m);
    }

    // Task 5: Zip two streams, stop when either is exhausted
    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> it1 = first.iterator();
        Iterator<T> it2 = second.iterator();

        Iterator<T> zipped = new Iterator<>() {
            private boolean fromFirst = true;

            @Override
            public boolean hasNext() {
                return it1.hasNext() && it2.hasNext();
            }

            @Override
            public T next() {
                if (fromFirst) {
                    fromFirst = false;
                    return it1.next();
                } else {
                    fromFirst = true;
                    return it2.next();
                }
            }
        };

        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(zipped, Spliterator.ORDERED),
                false
        );
    }

    public static void main(String[] args) {
        // Task 1
        List<String> names = List.of("Ivan", "Peter", "Olga", "Maria", "Taras", "Anna");
        System.out.println("Task 1: " + oddIndexedNames(names));

        // Task 2
        System.out.println("Task 2: " + upperCaseSortedDesc(names));

        // Task 3
        String[] array = {"1, 2, 0", "4, 5"};
        System.out.println("Task 3: " + sortedNumbers(array));

        // Task 4
        long a = 25214903917L;
        long c = 11;
        long m = (long) Math.pow(2, 48);
        System.out.println("Task 4: " + lcg(a, c, m).limit(10).collect(Collectors.toList()));

        // Task 5
        Stream<Integer> s1 = Stream.of(1, 2, 3, 4);
        Stream<Integer> s2 = Stream.of(10, 20, 30);
        System.out.println("Task 5: " + zip(s1, s2).collect(Collectors.toList()));
    }
}
