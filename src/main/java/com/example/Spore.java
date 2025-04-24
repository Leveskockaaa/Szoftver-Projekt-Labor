package com.example;

/**
 * A spóra osztályokat összefogó absztrakt osztály. Az általános tulajdonságokat valósítja meg.
 */
public abstract class Spore {
    /**
     * A spóra tápanyagérték tartalmát tárolja a rovar számára.
     */
    protected int nutrientValue;

    /**
     * Default constructor.
     */
    protected Spore(int nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    /**
     * Getter: Visszaadja a spóra tápanyagértékét.
     *
     * @return A spóra tápanyagértéke.
     */
    public int getNutrientValue() {
        return nutrientValue;
    }

    /**
     * Setter: Beállítja a spóra tápanyagértékét.
     *
     * @param nutrientValue A spóra tápanyagértéke.
     */
    public void setNutrientValue(int nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    /**
     * Absztrakt függvény, amit a leszármazottak valósítanak meg
     * egyesével. A spóra hatását fejti ki a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    public abstract void takeEffectOn(Insect insect);
}

