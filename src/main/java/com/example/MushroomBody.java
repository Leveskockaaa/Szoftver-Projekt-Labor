package com.example;

import java.util.List;

/**
 * Represents a MushroomBody in the domain model.
 * A Tecton can be haunted by a MushroomBody, which may alter its state.
 */
public abstract class MushroomBody {
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
    public MushroomBody(Tecton tecton) {
        this.tecton = tecton;
        this.superBody = false;
        this.dead = false;
        this.canSpreadSpores = true;
    }

    public void setMycologist(Mycologist mycologist) {
        this.mycologist = mycologist;
    }

    /**
     * Enables spore spread for this MushroomBody.
     */
    public void enableSporeSpread() {
        Skeleton.logFunctionCall(this, "enableSporeSpread");
        canSpreadSpores = true;
        Skeleton.logReturn(this, "enableSporeSpread");
    }

    /**
     * Evolves to Mushroom Body to Super Mushroom
     */
    public void evolveSuper() {
        Skeleton.logFunctionCall(this, "evolveSuper");
        if(canEvolve()){
            superBody = true;
            //System.out.println("Evolved");
        }
        Skeleton.logReturn(this, "evolveSuper");
    }

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
}

