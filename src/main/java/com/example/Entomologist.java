package com.example;

/**
 * Az Entomologist osztály egy játékos entomológust reprezentál, aki rovarokkal foglalkozik.
 * Az entomológus pontszámot gyűjt a játék során, és különböző műveleteket hajt végre a rovarokkal.
 */
public class Entomologist extends Player {

    private int score;

    /**
     * Konstruktor, amely inicializálja az entomológus nevét és pontszámát.
     *
     * @param name Az entomológus neve.
     */
    public Entomologist(String name) {
        super(name);
        this.score = 0;
    }

    /**
     * Elhelyezi az entomológust egy kezdeti Tecton-on.
     *
     * Ez a metódus felelős az entomológus kezdeti elhelyezéséért egy megadott Tecton-on.
     * Jelenleg nincs implementálva, és UnsupportedOperationException kivételt dob.
     *
     * @param on A Tecton, amelyen az entomológust el kell helyezni.
     * @throws UnsupportedOperationException Mindig dobódik, mivel a metódus nincs implementálva.
     */
    @Override
    public void placeInitial(Tecton on) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeInitial'");
    }

    /**
     * Beállítja az entomológus pontszámát.
     *
     * Ez a metódus hozzáadja a megadott pontszámot az entomológus aktuális pontszámához.
     * Először naplózza a függvényhívást, majd frissíti a pontszámot, és végül naplózza a visszatérést.
     *
     * @param score A pontszám, amelyet hozzá kell adni az entomológus aktuális pontszámához.
     */
    public void setScore(int score) {
        Skeleton.logFunctionCall(this, "setScore", Integer.toString(score));
        this.score += score;
        Skeleton.logReturn(this, "setScore");
    }
}