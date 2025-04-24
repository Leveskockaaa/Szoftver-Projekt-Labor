package com.example;

/**
 * Egy rovar entitást reprezentál különböző attribútumokkal és viselkedésekkel,
 * mint például micélium rágása, spórák evése, különböző Tecton-okra való mozgás stb.
 */
public class Insect {

    private Tecton tecton;

    private Entomologist entomologist;

    /**
     * A rovar neve.
     */
    private String name;

    /**
     * A rovar színe HEX formátumban.
     */
    private String color;

    /**
     * Az összes tápanyag pont, amit ez a rovar összegyűjtött.
     */
    private int collectedNutrientPoints;

    /**
     * A szorzó, amit a tápanyag pontok gyűjtésekor használnak.
     */
    private int nutrientMultiplier;

    /**
     * Jelzi, hogy a rovar képes-e micéliumot rágni.
     */
    private boolean canChewMycelium;

    /**
     * A rovar aktuális sebessége.
     */
    private float speed;

    /**
     * Jelzi, hogy a rovar bénult-e.
     */
    private boolean isParalized;

    /**
     * Jelzi, hogy a rovar képes-e enni.
     */
    private boolean canEat;

    /**
     * Alapértelmezett konstruktor.
     *
     * @param entomologist Az entomológus, akihez a rovar tartozik.
     */
    public Insect(Entomologist entomologist, String name) {
        this.entomologist = entomologist;
    }

    /**
     * Engedélyezi a rovar számára a micélium rágását.
     */
    public void enableToChewMycelium() {
        Skeleton.logFunctionCall(this, "enableToChewMycelium");

        this.canChewMycelium = true;

        Skeleton.logReturn(this, "enableToChewMycelium");
    }

    /**
     * Letiltja a rovar számára a micélium rágását.
     */
    public void disableChewMycelium() {
        Skeleton.logFunctionCall(this, "disableChewMycelium");

        this.canChewMycelium = false;

        Skeleton.logReturn(this, "disableChewMycelium");
    }

    /**
     * Lehetővé teszi a rovar számára, hogy megrágja a megadott micéliumot.
     *
     * @param mycelium A micélium objektum, amit meg kell rágni.
     */
    public void chewMycelium(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "chewMycelium", mycelium);

        if (this.canChewMycelium) {
            for (Mycelium m : this.getTecton().mycelia) {
                if (m.getMyceliumConnections().contains(mycelium)) {
                    m.removeConnection(mycelium);
                    mycelium.removeConnection(m);
                }
            }
        }

        Skeleton.logReturn(this, "chewMycelium");
    }

    /**
     * Beállítja a tápanyag szorzót ehhez a rovarhoz.
     *
     * @param times Az új szorzó értéke.
     */
    public void setNutrientMultiplier(int times) {
        Skeleton.logFunctionCall(this, "setNutrientMultiplier", Integer.toString(times));

        this.nutrientMultiplier = times;

        Skeleton.logReturn(this, "setNutrientMultiplier");
    }

    /**
     * Lehetővé teszi a rovar számára, hogy megegyen egy spórát.
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
     * Engedélyezi a rovar számára az evést.
     */
    public void enableEating() {
        Skeleton.logFunctionCall(this, "enableEating");

        this.canEat = true;

        Skeleton.logReturn(this, "enableEating");
    }

    /**
     * Letiltja a rovar számára az evést.
     */
    public void disableEating() {
        Skeleton.logFunctionCall(this, "disableEating");

        this.canEat = false;

        Skeleton.logReturn(this, "disableEating");
    }

    /**
     * Áthelyezi a rovart a megadott Tecton-ra.
     *
     * @param t A Tecton, ahová a rovart át kell helyezni.
     */
    public void moveTo(Tecton t) {
        Skeleton.logFunctionCall(this, "moveTo", t);

        t.placeInsect(this);

        Skeleton.logReturn(this, "moveTo");
    }

    /**
     * Levon egy tápanyag pontot a rovarból.
     */
    public void deductNutrientPoint() {
        Skeleton.logFunctionCall(this, "deductNutrientPoint");

        this.entomologist.setScore(-1);

        Skeleton.logReturn(this, "deductNutrientPoint");
    }

    /**
     * Semlegesíti a spórák hatásait.
     */
    public void neutralizeSporeEffects() {
        // TODO: Add logic here
    }

    /**
     * Semlegesíti a tektonok hatásait.
     */
    public void neutralizeTectonEffects() {
        Skeleton.logFunctionCall(this, "neutralizeTectonEffects");

        setNutrientMultiplier(1);

        Skeleton.logReturn(this, "neutralizeTectonEffects");
    }

    /**
     * Beállítja a rovar sebességét a megadott értékre.
     *
     * @param speed A rovar új sebessége.
     */
    public void setSpeed(float speed) {
        Skeleton.logFunctionCall(this, "setSpeed", Float.toString(speed));

        this.speed = speed;

        Skeleton.logReturn(this, "setSpeed");
    }

    /**
     * Visszaállítja a rovar sebességét az alapértelmezett értékre.
     */
    public void resetSpeed() {
        // TODO: Add logic here
    }

    /**
     * Bénítja a rovart.
     */
    public void paralize() {
        Skeleton.logFunctionCall(this, "paralize");

        this.isParalized = true;

        Skeleton.logReturn(this, "paralize");
    }

    /**
     * Eltávolítja a bénultságot a rovarról.
     */
    public void unParalized() {
        Skeleton.logFunctionCall(this, "unParalized");

        this.isParalized = false;

        Skeleton.logReturn(this, "unParalized");
    }

    /**
     * Ellenőrzi, hogy a rovar bénult-e.
     *
     * @return true, ha a rovar bénult, különben false.
     */
    public boolean isParalized() {
        return this.isParalized;
    }

    /**
     * Beállítja a rovar tektonját a megadott tektonra.
     *
     * @param tecton A tekton, amit be kell állítani.
     */
    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * Visszaadja a rovar aktuális tektonját.
     *
     * @return A rovar aktuális tektonja.
     */
    public Tecton getTecton() {
        return this.tecton;
    }

    /**
     * Beállítja a rovar entomológusát a megadott entomológusra.
     *
     * @param entomologist Az entomológus, amit be kell állítani.
     */
    public void setEntomologist(Entomologist entomologist) {
        this.entomologist = entomologist;
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printName() {
        return this.name;
    }

    public String printCollectedNutrientPoints() {
        return Integer.toString(this.collectedNutrientPoints);
    }

    public String printNutrientMultiplier() {
        return Integer.toString(this.nutrientMultiplier);
    }

    public String printCanChewMycelium() {
        return canChewMycelium ? "Yes" : "No";
    }

    public String printCanEat() {
        return canEat ? "Yes" : "No";
    }

    public String printSpeed() {
        return Float.toString(this.speed);
    }

    public String printIsParalized() {
        return isParalized ? "Yes" : "No";
    }

    public String printTecton() {
        return this.tecton.printName();
    }
}