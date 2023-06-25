package streamloop.compare;

import streamloop.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountItemsCompareUnit extends CompareUnit {
    public CountItemsCompareUnit() {
        super("Count items");
    }

    @Override
    public boolean loop(List<Item> items) {
        Map<Item.Type, Integer> map = new HashMap<>();
        for (Item item : items) {
            map.compute(item.type(), (key, value) -> {
                if (value == null) return 1;
                return value + 1;
            });
        }
        return true;
    }

    @Override
    public boolean stream(List<Item> items) {
        //noinspection ResultOfMethodCallIgnored
        items.stream().collect(Collectors.toMap(
                Item::type,
                value -> 1,
                Integer::sum));
        return true;
    }

    @Override
    public boolean parallel(List<Item> items) {
        return false;
    }
}
