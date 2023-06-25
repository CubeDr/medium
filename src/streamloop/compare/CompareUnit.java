package streamloop.compare;

import streamloop.Item;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class CompareUnit {
    public final String title;

    protected CompareUnit(String title) {
        this.title = title;
    }

    /**
     * Returns true if implemented, false if not.
     */
    protected abstract boolean loop(List<Item> items);

    /**
     * Returns true if implemented, false if not.
     */
    protected abstract boolean stream(List<Item> items);

    /**
     * Returns true if implemented, false if not.
     */
    protected abstract boolean parallel(List<Item> items);

    public record ExecutionResult(Optional<Double> loop,
                                  Optional<Double> stream,
                                  Optional<Double> parallel) {
    }

    public ExecutionResult execute(List<Item> items, int rounds) {
        return new ExecutionResult(
                measureAverage(rounds, this::loop, items),
                measureAverage(rounds, this::stream, items),
                measureAverage(rounds, this::parallel, items)
        );
    }

    private static Optional<Long> measure(Function<List<Item>, Boolean> method, List<Item> argument) {
        long start = System.currentTimeMillis();

        boolean isImplemented = method.apply(argument);
        if (!isImplemented) return Optional.empty();

        long end = System.currentTimeMillis();

        return Optional.of(end - start);
    }

    private static Optional<Double> measureAverage(int rounds, Function<List<Item>, Boolean> method, List<Item> items) {
        long sum = 0;
        for (int i = 0; i < rounds; i++) {
            Optional<Long> executionTime = measure(method, items);
            if (executionTime.isEmpty()) return Optional.empty();

            sum += executionTime.get();
        }
        return Optional.of((double) sum / rounds);
    }
}
