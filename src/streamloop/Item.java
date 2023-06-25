package streamloop;

import java.util.Random;

public record Item(Type type, String name) {
    public enum Type {
        WEAPON, ARMOR, HELMET, GLOVES, BOOTS,
    }

    private static final Random random = new Random();
    private static final String[] NAMES = {
            "beginner",
            "knight",
            "king",
            "dragon",
    };

    public static Item random() {
        return new Item(
                Type.values()[random.nextInt(Type.values().length)],
                NAMES[random.nextInt(NAMES.length)]);
    }
}
