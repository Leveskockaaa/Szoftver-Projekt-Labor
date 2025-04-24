package com.example;

/**
 * A spóra osztályokat összefogó absztrakt osztály. Az általános tulajdonságokat valósítja meg.
 */
public abstract class Spore {
    /**
     * A spórát létrehozó gombatestet referenciája.
     */
    protected MushroomBody mushroomBody;

    /**
     * A spóra tápanyagérték tartalmát tárolja a rovar számára.
     */
    protected int nutrientValue;

    /**
     * Konstruktor.
     * 
     * @param mushroomBody A spórát létrehozó gombatest.
     * @param nutrientValue A spóra tápanyagértéke.
     */
    protected Spore(MushroomBody mushroomBody, int nutrientValue) {
        this.mushroomBody = mushroomBody;
        this.nutrientValue = nutrientValue;
    }

    /**
     * Getter: Visszaadja a spórát létrehozó gombatestet.
     *
     * @return A spórát létrehozó gombatest.
     */
    public MushroomBody getMushroomBody() {
        return mushroomBody;
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

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public abstract String printType();

}

