package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Entomologist osztály egy játékos entomológust reprezentál, aki rovarokkal foglalkozik.
 * Az entomológus pontszámot gyűjt a játék során, és különböző műveleteket hajt végre a rovarokkal.
 */
public class Entomologist extends Player {

    private List<Insect> insects;

    /**
     * Konstruktor, amely inicializálja az entomológus nevét és pontszámát.
     *
     * @param name Az entomológus neve.
     */
    public Entomologist(String name) {
        super(name);
        insects = new ArrayList<Insect>();
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
        insects.get(0).setTecton(on);
        on.placeInsect(insects.get(0));
    }

    public void addInsect(Insect i) { insects.add(i); }
    public void removeInsect(Insect i) { insects.remove(i); }
}