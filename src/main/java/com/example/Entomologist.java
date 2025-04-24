package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Entomologist osztály egy játékos entomológust reprezentál, aki rovarokkal foglalkozik.
 * Az entomológus pontszámot gyűjt a játék során, és különböző műveleteket hajt végre a rovarokkal.
 */
public class Entomologist extends Player {

    private int score;

    private List<Insect> insects;

    /**
     * Konstruktor, amely inicializálja az entomológus nevét és pontszámát.
     *
     * @param name Az entomológus neve.
     */
    public Entomologist(String name) {
        super(name);
        this.score = 0;
        this.insects = new ArrayList<>();
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

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printType() {
        return this.getClass().getSimpleName();
    }

    public String printInsects() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Insect insect : insects) {
            sb.append(insect.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}