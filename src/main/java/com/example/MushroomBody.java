package com.example;

import java.util.List;
import java.util.Random;

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
    protected MushroomBody(Tecton tecton, Mycologist mycologist) {
        this.mycologist = mycologist;
        this.tecton = tecton;
        this.superBody = false;
        this.dead = false;
        this.canSpreadSpores = true;
    }

    public static MushroomBody createRandomMushroomBody(Tecton tecton, Mycologist mycologist) {
        int type = new Random().nextInt(3);
        switch (type) {
            case 0 -> {
                return new Capulon(tecton, mycologist);
            }
            case 1 -> {
                return new Gilledon(tecton, mycologist);
            }
            case 2 -> {
                return new Hyphara(tecton, mycologist);
            }
            case 3 -> {
                return new Poralia(tecton, mycologist);
            }
            default -> throw new AssertionError();
        }
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

