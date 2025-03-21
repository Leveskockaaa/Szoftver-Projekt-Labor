package com.example;

/**
 * Represents a Spore in the domain model.
 * Spores can be added to or removed from Tectons, and can affect Insects.
 */
public abstract class Spore {

    /**
     * The amount of nutrients the Spore provides.
     */
    protected int nutrientValue;


    /**
     * Default constructor.
     */
    protected Spore(int nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    /**
     * Effects the Insect that has eaten the Spore
     * 
     * @param insect
     */
    public abstract void takeEffectOn(Insect insect);


    public int getNutrientValue() {
        return nutrientValue;
    }

    public void setNutrientValue(int nutrientValue) {
        this.nutrientValue = nutrientValue;
    }


}

