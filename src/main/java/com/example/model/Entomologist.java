package com.example.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Az Entomologist osztály egy játékos entomológust reprezentál, aki rovarokkal foglalkozik.
 * Az entomológus pontszámot gyűjt a játék során, és különböző műveleteket hajt végre a rovarokkal.
 */
public class Entomologist extends Player {

    private List<Insect> insects;

    private Color color;

    public Entomologist() {
        super();
        insects = new ArrayList<Insect>();
        insects.add(new Insect(this));
        score = 0;
    }

    public void setColor(Color c) { color = c; }

    public Color getColor() { return color; }

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
        on.placeInsect(insects.get(0));
    }

    public List<Insect> getInsects() {
        return insects;
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
    public void addInsect(Insect i) { insects.add(i); }
    public void removeInsect(Insect i) { insects.remove(i); }
}