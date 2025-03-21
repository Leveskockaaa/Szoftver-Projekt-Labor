package com.example;

/**
 * Represents a MushroomBody in the domain model.
 * A Tecton can be haunted by a MushroomBody, which may alter its state.
 */
public abstract  class MushroomBody {

    
    protected boolean superBody;

    protected boolean dead;

    protected boolean canSpreadSpores;

    protected int sporeSpreadsLeft;




    /**
     * Default constructor.
     */
    public MushroomBody() {
        // No initialization logic required
    }

    /**
     * Enables spore spread for this MushroomBody.
     */
    public void enableSporeSpread() {
        // TODO: Implement this method
    }

    /**
     * Evolves to Mushroom Body to Super Mushroom
     */
    public  void evolveSuper() {
        // TODO: Implement this method
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

