package com.example;

import java.util.List;

/**
 * Represents a MushroomBody in the domain model.
 * A Tecton can be haunted by a MushroomBody, which may alter its state.
 */
public abstract class MushroomBody {
    protected boolean superBody;
    protected boolean dead;
    protected boolean canSpreadSpores;
    protected int sporeSpreadsLeft;

    protected Mycologist mycologist;
    protected Tecton tecton;
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

