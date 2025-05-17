package com.example.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A gombákat irányító játékosokat hivatott kezelni ez az osztály.
 */
public class Mycologist extends Player {
    /**
     * A gombász színe.
     */
    private Color color;
    /**
     * Az gombászhoz tartozó élő gombatestek listája.
     */
    private List<MushroomBody> mushroomBodies = new ArrayList<>();
    /**
     * A gombászhoz tartozó kezdeti gombatest.
     */
    private MushroomBody initialMushroomBody = null;

    /**
     * A gombász által begyűjtött halott gombatestjeinek listája.
     */
    private List<MushroomBody> bag = new ArrayList<MushroomBody>();

    /**
     * A gombászhoz tartozó gombafonalak listája.
     */
    private List<Mycelium> mycelia = new ArrayList<Mycelium>();


    private String type = null;

    /**
     * Mycologist konstruktora, amiben megadhatjuk a játékos nevét.
     *
     * @param name A játékos neve.
     */
    public Mycologist() {
        super();

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

    public void setMushroomBodyType(MushroomBody mb) {
        this.initialMushroomBody = mb;
    }

    public void addMushroomBody(MushroomBody mb) {mushroomBodies.add(mb);}

    public MushroomBody createMushroomBody(Tecton tecton, String name) {

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
        if (mb.isDead()){
            mushroomBodies.remove(mb);
            bag.add(mb);
        }
    }

    public void addMycelium(Mycelium my){
        mycelia.add(my);
    }

    public List<Mycelium> getMycelia() {
        return mycelia;
    }

    /**
     * Kitöröl egy gombafonalat a gombafonalait tároló listájából.
     * @param my a gombafonál amit törölni szeretnénk.
     */
    public void removeMycelium(Mycelium my) {
        mycelia.remove(my);
    }

    /**
     * Elhelyezi a játékos gombatestét a kezdő tektonra.
     * @param on A kezdő tektonunk.
     */
    public void placeInitial(Tecton on){
        on.placeMushroomBody(mushroomBodies.get(0));
    }

    /**
     * Getter a gombász színéhez.
     * @return A gombász színe.
     */
    public Color getColor() {
        return color;
    }
    /**
     * Setter a gombász színéhez.
     * @param color A gombász színe.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
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
        return this.initialMushroomBody.getClass().getSimpleName();
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
        for (Mycelium my : mycelia) {
            sb.append(my.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
}
