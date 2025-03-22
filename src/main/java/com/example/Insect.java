package com.example;

/**
 * Represents an Insect entity with various attributes and behaviors
 * such as chewing mycelium, eating spores, moving to different Tectons, etc.
 */
public class Insect {


    private Tecton tecton;

    private Entomologist entomologist;

    /**
     * The name of the insect.
     */
    private String name;

    /**
     * The color of the insect in HEX format.
     */
    private String color;

    /**
     * The total nutrient points this insect has collected.
     */
    private int collectedNutrientPoints;

    /**
     * The multiplier used when collecting nutrient points.
     */
    private int nutrientMultiplier;

    /**
     * Indicates whether the insect can chew mycelium.
     */
    private boolean canChewMycelium;

    /**
     * The current speed of the insect.
     */
    private float speed;

    /**
     * Indicates whether the insect is paralyzed.
     */
    private boolean isParalized;

    /**
     * Indicates whether the insect can eat.
     */
    private boolean canEat;

    /**
     * Default constructor.
     */
    public Insect(Entomologist entomologist) {
        this.entomologist = entomologist;
        
    }

    /**
     * Enables the insect to chew mycelium.
     */
    public void enableToChewMycelium() {
        Skeleton.logFunctionCall(this, "enableToChewMycelium");

        this.canChewMycelium = true;

        Skeleton.logReturn(this, "enableToChewMycelium");
    }

    /**
     * Disables the insect from chewing mycelium.
     */
    public void disableChewMycelium() {
        Skeleton.logFunctionCall(this, "disableChewMycelium");

        this.canChewMycelium = false;

        Skeleton.logReturn(this, "disableChewMycelium");
    }

    /**
     * Allows the insect to chew the given mycelium.
     *
     * @param mycelium The Mycelium object to be chewed.
     */
    public void chewMycelium(Mycelium mycelium) {
        // TODO: Add logic here
    }

    /**
     * Sets the nutrient multiplier for this insect.
     *
     * @param times The new multiplier value.
     */
    public void setNutrientMultiplier(int times) {
        // TODO: Add logic here
    }

    /**
     * Allows the insect to eat a spore.
     */
    public void eatSpore() {

        Skeleton.logFunctionCall(this, "eatSpore");

        Spore s1 = tecton.removeOldestSpore();

        entomologist.setScore(s1.getNutrientValue());

        if (!(tecton instanceof Orogenix)) {
            s1.takeEffectOn(this);
        }

        Skeleton.logReturn(this, "eatSpore");
    }

    /**
     * Enables the insect to eat.
     */
    public void enableEating() {
        Skeleton.logFunctionCall(this, "enableEating");

        this.canEat = true;
        
        Skeleton.logReturn(this, "enableEating");
   
    }

    /**
     * Disables the insect from eating.
     */
    public void disableEating() {
        Skeleton.logFunctionCall(this, "disableEating");

        this.canEat = false;

        Skeleton.logReturn(this, "disableEating");
    }

    /**
     * Moves the insect to the specified Tecton.
     *
     * @param t The Tecton to move to.
     */
    public void moveTo(Tecton t) {
        // TODO: Add logic here
    }

    /**
     * Deducts one nutrient point from the insect.
     */
    public void deductNutrientPoint() {
        // TODO: Add logic here
    }

    /**
     * Neutralizes any effects from spores.
     */
    public void neutralizeSporeEffects() {
        // TODO: Add logic here
    }

    /**
     * Neutralizes any effects from tectons.
     */
    public void neutralizeTectonEffects() {
        // TODO: Add logic here
    }

    /**
     * Sets the insect's speed to the specified value.
     *
     * @param speed The new speed of the insect.
     */
    public void setSpeed(float speed) {
        Skeleton.logFunctionCall(this, "setSpeed", Float.toString(speed));

        this.speed = speed;

        Skeleton.logReturn(this, "setSpeed");
    }

    /**
     * Resets the insect's speed to its default value.
     */
    public void resetSpeed() {
        // TODO: Add logic here
    }

    /**
     * Paralyzes the insect.
     */
    public void paralize() {
        Skeleton.logFunctionCall(this, "paralize");

        this.isParalized = true;

        Skeleton.logReturn(this, "paralize");
    }

    /**
     * Removes paralysis from the insect.
     */
    public void unParalized() {
        Skeleton.logFunctionCall(this, "unParalized");

        this.isParalized = false;

        Skeleton.logReturn(this, "unParalized");
    }

    /**
     * Checks whether the insect is paralyzed.
     *
     * @return true if the insect is paralyzed, false otherwise.
     */
    public boolean isParalized() {
       
        return this.isParalized;
    }


    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    public void setEntomologist(Entomologist entomologist) {
        this.entomologist = entomologist;
    }
   
}
