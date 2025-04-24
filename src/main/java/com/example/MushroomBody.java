package com.example;

import java.util.List;

/**
 * Represents a MushroomBody in the domain model.
 * A Tecton can be haunted by a MushroomBody, which may alter its state.
 */
public abstract class MushroomBody {
    /**
     * A gombatesthez tartozó egyedi név.
     */
    protected String name;
    /**
     * Egy igaz-hamis érték arról, hogy a gombatest szupergomba-e.
     */
    protected boolean superBody;

    /**
     * Egy igaz-hamis érték. Azt jelöli, hogy a gomba halott-e.
     */
    protected boolean dead;

    /**
     * Értéke megadja, hogy a gombatest tud-e jelenleg spórát szórni.
     */
    protected boolean canSpreadSpores;

    /**
     * A hátralévő spóra szórások száma.
     */
    protected int sporeSpreadsLeft;

    /**
     * A gombatesthez tartozó gombász.
     */
    protected Mycologist mycologist;

    /**
     * A tekton amin a gombatest elhelyezkedik.
     */
    protected Tecton tecton;

    /**
     * A gombatesthez tartozó gombafonalak.
     */
    protected List<Mycelium> myceliums;

    /**
     * Default constructor.
     */
    protected MushroomBody(Tecton tecton, Mycologist mycologist) {
        this.mycologist = mycologist;
        this.tecton = tecton;
        this.superBody = false;
        this.dead = false;
        this.canSpreadSpores = true;
    }

    /**
     * Getter a gombatesthez tartozó tektonhoz.
     * @return A gombatesthez tartozó tekton.
     */
    public Tecton getTecton() {
        return tecton;
    }

    public void setMycologist(Mycologist mycologist) {
        this.mycologist = mycologist;
    }

    /**
     * Enables spore spread for this MushroomBody.
     */
    public void enableSporeSpread() {
        canSpreadSpores = true;
    }

    /**
     * Evolves to Mushroom Body to Super Mushroom
     */
    public abstract void evolveSuper();

    /**
     * Spreads spores.
     */
    public abstract void spreadSpores();

    /**
     * Determines if the MushroomBody can evolve.
     *
     * @return true if the MushroomBody can evolve, false otherwise
     */
    public abstract boolean canEvolve();

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printName() {
        return name;
    }

    public abstract String printType();

    public String printLevel() {
        return superBody ? "Super" : "Normal";
    }

    public String printState() {
        return dead ? "Dead" : "Alive";
    }

    public String printSporeSpread() {
        return canSpreadSpores ? "Yes" : "No";
    }

    public String printSporeSpreadsLeft() {
        return String.valueOf(sporeSpreadsLeft);
    }
}

