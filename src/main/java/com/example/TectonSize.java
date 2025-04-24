package com.example;

/**
 * A Tecton méretét reprezentáló enum.
 */
public enum TectonSize {
    GIANT,
    BIG,
    MEDIUM,
    SMALL;

    /**
     * Csökkenti a megadott Tecton méretét a következő kisebb méretre.
     *
     * @param size A jelenlegi Tecton méret.
     * @return A következő kisebb Tecton méret, vagy null, ha a méret már a legkisebb (SMALL).
     */
    public static TectonSize decreaseSize(TectonSize size) {
        return switch (size) {
            case GIANT -> BIG;
            case BIG -> MEDIUM;
            case MEDIUM -> SMALL;
            default -> null; // SMALL a legkisebb méret
        };
    }
}
