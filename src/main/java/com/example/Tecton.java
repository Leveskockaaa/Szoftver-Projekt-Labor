package com.example;

import java.util.*;


/**
 * Represents a Tecton, which is an abstract concept in your domain model.
 * A Tecton can hold spores, mycelium, and potentially a mushroom body.
 * It can also break apart, become haunted, etc.
 */
public abstract class Tecton {

    protected HashSet<Tecton> neighbors;

    protected MushroomBody mushroomBody;

    protected Mycelium mycelium;

    /**
     * The unique identifier of this Tecton.
     */
    protected int id;

    /**
     * The size of this Tecton.
     */
    protected TectonSize size;

    /**
     * The maximum number of Mycelia that can be placed on this Tecton.
     */
    protected int maxMycelia;


    protected List<Spore> spores;

    /**
     * Default constructor.
     */
    protected Tecton() {

        spores = new ArrayList<>();
        neighbors = new HashSet<>();
    }

    /**
     * Constructs the game map.
     *
     */
    public List<Tecton> generateMap() {
        return null;
    }

    /**
     * Breaks this Tecton apart and returns a new Tecton instance.
     *
     * @return A new Tecton object resulting from the break.
     */
    public List<Tecton> breakApart() {
        Skeleton.logFunctionCall(this, "breakApart");

        Transix t1 = new Transix();
        Skeleton.logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        Skeleton.logCreateInstance(t2, "Transix", "t2");

        neighbors.add(t1);
        neighbors.add(t2);

        t1.placeMushroomBody(this.mushroomBody);
        t1.addMycelium(this.mycelium);

        Tecton n2 = neighbors.iterator().next();
        Tecton n1 = neighbors.iterator().next();
        neighbors.remove(n2);
        n1.changeNeighbour(this, t2);

        neighbors.remove(n1);
        n2.changeNeighbour(this, t1);

        Skeleton.logReturn(this, "breakApart");
        return new ArrayList<>(Arrays.asList(t1, t2));
    }

    public void addTectonToNeighbors(Tecton tecton) {
        if (!neighbors.contains(tecton)) {
            Skeleton.logFunctionCall(this, "addTectonToNeighbors", tecton);
            neighbors.add(tecton);
            tecton.addTectonToNeighbors(this);
            Skeleton.logReturn(this, "addTectonToNeighbors");
        }
    }

    public void changeNeighbour(Tecton from, Tecton to) {
        Skeleton.logFunctionCall(this, "changeNeighbour", from, to);
        neighbors.remove(from);
        neighbors.add(to);
        to.addTectonToNeighbors(this);
        Skeleton.logReturn(this, "changeNeighbour");
    }

    public void removeTectonFromNeighbors(Tecton tecton) {
        // TODO: Implement logic
    }

    /**
     * Checks whether this Tecton has a MushroomBody.
     * @return true if it has a MushroomBody, false otherwise.
     */
    public boolean hasMushroomBody() {
        // TODO: Implement logic
        return false;
    }


    /**
     * Places the specified Insect on this Tecton.
     * @param insect The insect to be placed.
     */
    public abstract void placeInsect(Insect insect);


    /**
     * Places the specified MushroomBody on this Tecton.
     * @param mushroomBody The MushroomBody to be placed.
     */
    public abstract void placeMushroomBody(MushroomBody mushroomBody);

    /**
     * Removes the MushroomBody from this Tecton.
     * @return The removed MushroomBody.
     */
    public MushroomBody removeMushroomBody() {
        // TODO: Implement logic
        return null;
    }

    /**
     * Removes the Insect from this Tecton.
     */
    public void removeInsect() {
        // TODO: Implement logic
    }

    /**
     * Adds the specified Spore to this Tecton.
     *
     * @param spore    The Spore to add.
     */
    public void addSpore(Spore spore) {
        Skeleton.logFunctionCall(this, "addSpore", spore);
        spores.add(spore);
        Skeleton.logReturn(this, "addSpore");
    }


    /**
     * Returns a list of Spores available on this Tecton.
     * 
     * @return A list of Spores available.
     */
    public List<Spore> sporesAvailable() {
        // TODO: Implement logic
        return null;
    }

    /**
     * Takes the given quantity of the specified Spore from this Tecton.
     *
     * @param spore    The Spore to remove.
     * @param quantity The quantity of spores to remove.
     * @return true if the spores were successfully removed, false otherwise
     */
    public boolean takeSpore(Spore spore, int quantity) {
        // TODO: Implement logic
        return false;
    }


    /**
     * Removes the oldest Spore from this Tecton.
     *
     * @return The removed Spore.
     */
    public Spore removeOldestSpore() {
        Skeleton.logFunctionCall(this, "removeOldestSpore");
        Spore spore = spores.remove(0);
        
        Skeleton.logReturn(this, "removeOldestSpore");
        return spore;
    }

    /**
     * Returns the number of neighbors this Tecton has.
     *
     * @return The number of neighbors.
     */
    public int neighborCount() {
        // TODO: Implement logic
        return 0;
    }

    
    /**
     * Determines if Mycelium can be added to this Tecton.
     *
     * @return true if Mycelium can be added, false otherwise.
     */
    public abstract boolean canAddMycelium();

    /**
     * Places the specified Mycelium on this Tecton.
     *
     * @param mycelium The Mycelium to place.
     */
    public abstract void addMycelium(Mycelium mycelium);

    /**
     * Removes the specified Mycelium from this Tecton.
     *
     * @param mycelium The Mycelium to remove.
     * @return true if successfully removed, false otherwise.
     */
    public boolean removeMycelium(Mycelium mycelium) {
        // TODO: Implement logic
        return false;
    }

}
