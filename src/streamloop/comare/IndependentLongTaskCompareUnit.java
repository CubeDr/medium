package streamloop.comare;

import streamloop.Item;

import java.util.List;

public class IndependentLongTaskCompareUnit extends CompareUnit {
    public IndependentLongTaskCompareUnit() {
        super("independent long task");
    }

    private void longTask() {
        // Mock long task.
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean loop(List<Item> items) {
        for (Item item : items) {
            longTask();
        }
        return true;
    }

    @Override
    protected boolean stream(List<Item> items) {
        items.stream().forEach(item -> longTask());
        return true;
    }

    @Override
    protected boolean parallel(List<Item> items) {
        items.parallelStream().forEach(item -> longTask());
        return true;
    }
}
