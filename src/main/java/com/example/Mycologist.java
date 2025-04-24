package com.example;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * A gombákat irányító játékosokat hivatott kezelni ez az osztály.
 */
public class Mycologist extends Player {
    /**
     * Az gombászhoz tartozó élő gombatestek listája.
     */
    private List<MushroomBody> mushroomBodies = new ArrayList<>();

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
     *
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

    public MushroomBody createMushroomBody(Tecton tecton) {
        MushroomBody initialMushroomBody = bag.getFirst();

        try {
            if (initialMushroomBody == null) {
                throw new IllegalArgumentException("No initial mushroombody created");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        return initialMushroomBody.createMushroomBody(tecton, this);
    }

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

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printType() {
        return this.getClass().getSimpleName();
    }

    public String printSpecies() {
        return this.mushroomBodies.get(0).getClass().getSimpleName();
    }

    public String printMushroomBodies() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (MushroomBody mb : mushroomBodies) {
            sb.append(mb.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }

    public String printBag() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (MushroomBody mb : bag) {
            sb.append(mb.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }

    public String printMycelia() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Mycelium my : myceliums) {
            sb.append(my.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
