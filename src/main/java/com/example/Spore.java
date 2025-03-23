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
     * @param insect
     */
    public abstract void takeEffectOn(Insect insect);

    /**
     * Getter for the nutrient value of the spore.
     * @return The nutrient value of the spore.
     */
    public int getNutrientValue() {
        return nutrientValue;
    }

    /**
     * Setter for the nutrient value of the spore.
     * @param nutrientValue The new nutrient value of the spore.
     */
    public void setNutrientValue(int nutrientValue) {
        this.nutrientValue = nutrientValue;
    }
}

