package streamloop;

import streamloop.compare.CompareUnit;
import streamloop.compare.CountItemsCompareUnit;
import streamloop.compare.IndependentLongTaskCompareUnit;

import java.util.List;
import java.util.stream.Stream;

public class TimeComparison {

    private static void compare(CompareUnit compareUnit, int rounds, List<Item> items) {
        CompareUnit.ExecutionResult result = compareUnit.execute(items, rounds);

        System.out.println(items.size() + "," + result.loop().map(String::valueOf).orElse("-") +
                "," + result.stream().map(String::valueOf).orElse("-") +
                "," + result.parallel().map(String::valueOf).orElse("-"));
    }

    private static void test(CompareUnit compareUnit, int[] lengths) {
        System.out.println("[" + compareUnit.title + "]");
        for (int length : lengths) {
            List<Item> items = Stream.generate(Item::random).limit(length).toList();
            compare(compareUnit, 100, items);
        }
    }

    public static void main(String[] args) {
        test(new CountItemsCompareUnit(), new int[]{10000, 100000, 1000000, 10000000, 100000000});
        test(new IndependentLongTaskCompareUnit(), new int[]{10, 50, 100, 300, 500, 800, 1000});
    }
}
