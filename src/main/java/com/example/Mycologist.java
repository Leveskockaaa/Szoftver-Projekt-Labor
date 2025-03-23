package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * A gombákat irányító játékosokat hivatott kezelni ez az osztály.
 */
public class Mycologist extends Player{
    /**
     * Az gombászhoz tartozó élő gombatestek listája.
     */
    private List<MushroomBody> mushroomBodies = new ArrayList<MushroomBody>();;

    /**
     * A gombász által begyűjtött halott gombatestjeinek listája.
     */
    private List<MushroomBody> bag;

    /**
     * A gombászhoz tartozó gombafonalak listája.
     */
    private List<Mycelium> myceliums;

    /**
     * Mycologist konstruktora, amiben megadhatjuk a játékos nevét.
     * @param name A játékos neve.
     */
    Mycologist(String name) {
        super(name);

        // Ez csak az adott tesztesetek belső működés nélküli megvalósításához szükséges, később törlendő.
        // (A mycelium.getType()-hoz kell, mert a mycelium csak úgy tudja, hogy milyen típusú gombafajhoz
        // tartozik, hogy megnézi a gombász gombatesteit, ez a funkció viszont nem része a skeletonnak)
        mushroomBodies.add(new Hyphara(new Transix()));
    }

    /**
     * Getter az eltárolt, még élő mushroomBody objektumok listájához.
     * @return Az elő gombatestek listája.
     */
    public List<MushroomBody> getMushroomBodies() {
        return mushroomBodies;
    }

    /**
     * Egy elhalt gombatestet a bag attribútum listába helyez.
     * @param mb Az elhalt gombatest.
     */
    public void collect(MushroomBody mb){
        Skeleton.logFunctionCall(this, "collect", mb);

        Skeleton.logReturn(this, "collect");
    }

    /**
     * Kitöröl egy gombafonalat a gombafonalait tároló listájából.
     * @param my a gombafonál amit törölni szeretnénk.
     */
    public void removeMycelium(Mycelium my) {
        Skeleton.logFunctionCall(this, "removeMycelium", my);

        Skeleton.logReturn(this, "removeMycelium");
    }

    /**
     * Beállítja a score attribútum értékét s-re.
     * @param s Az új score érték.
     */
    public void setScore(int s){
        Skeleton.logFunctionCall(this, "setScore", score);

        Skeleton.logReturn(this, "setScore");
    }

    /**
     * Elhelyezi a játékos gombatestét a kezdő tektonra.
     * @param on A kezdő tektonunk.
     */
    public void placeInitial(Tecton on){
        Skeleton.logFunctionCall(this, "placeInitial", on);

        Skeleton.logReturn(this, "placeInitial");
    }
}
