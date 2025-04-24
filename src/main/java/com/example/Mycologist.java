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
    private List<MushroomBody> bag = new ArrayList<MushroomBody>();

    /**
     * A gombászhoz tartozó gombafonalak listája.
     */
    private List<Mycelium> mycelia = new ArrayList<Mycelium>();

    /**
     * Mycologist konstruktora, amiben megadhatjuk a játékos nevét.
     * @param name A játékos neve.
     */
    Mycologist(String name) {
        super(name);

        // Ez csak az adott tesztesetek belső működés nélküli megvalósításához szükséges, később törlendő.
        // (A mycelium.getType()-hoz kell, mert a mycelium csak úgy tudja, hogy milyen típusú gombafajhoz
        // tartozik, hogy megnézi a gombász gombatesteit, ez a funkció viszont nem része a skeletonnak)
        //mushroomBodies.add(new Hyphara(new Transix()));
    }

    /**
     * Getter az eltárolt, még élő mushroomBody objektumok listájához.
     * @return Az elő gombatestek listája.
     */
    public List<MushroomBody> getMushroomBodies() {
        return mushroomBodies;
    }

    public void addMushroomBody(MushroomBody mb) {mushroomBodies.add(mb);}

    /**
     * Egy elhalt gombatestet a bag attribútum listába helyez.
     * @param mb Az elhalt gombatest.
     */
    public void collect(MushroomBody mb){
        Skeleton.logFunctionCall(this, "collect", mb);
        if (mb.isDead()){
            mushroomBodies.remove(mb);
            bag.add(mb);
        }
        Skeleton.logReturn(this, "collect");
    }

    public void addMycelium(Mycelium my){
        mycelia.add(my);
    }

    /**
     * Kitöröl egy gombafonalat a gombafonalait tároló listájából.
     * @param my a gombafonál amit törölni szeretnénk.
     */
    public void removeMycelium(Mycelium my) {
        Skeleton.logFunctionCall(this, "removeMycelium", my);
        mycelia.remove(my);
        Skeleton.logReturn(this, "removeMycelium");
    }

    /**
     * Elhelyezi a játékos gombatestét a kezdő tektonra.
     * @param on A kezdő tektonunk.
     */
    public void placeInitial(Tecton on){
        Skeleton.logFunctionCall(this, "placeInitial", on);
        mushroomBodies.get(0).setTecton(on);
        on.placeMushroomBody(mushroomBodies.get(0));
        Skeleton.logReturn(this, "placeInitial");
    }
}
