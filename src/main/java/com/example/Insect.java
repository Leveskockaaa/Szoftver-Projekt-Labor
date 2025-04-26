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
        this.name = name;
    }

    /**
     * Engedélyezi a rovar számára a micélium rágását.
     */
    public void enableToChewMycelium() {


        this.canChewMycelium = true;


    }

    /**
     * Letiltja a rovar számára a micélium rágását.
     */
    public void disableChewMycelium() {


        this.canChewMycelium = false;


    }

    /**
     * Lehetővé teszi a rovar számára, hogy megrágja a megadott micéliumot.
     *
     * @param mycelium A micélium objektum, amit meg kell rágni.
     */
    public void chewMycelium(Mycelium mycelium) {

        if (this.canChewMycelium) {
            for (Mycelium m : this.getTecton().mycelia) {
                if (m.getMyceliumConnections().contains(mycelium)) {
                    m.removeConnection(mycelium);
                    mycelium.removeConnection(m);
                }
            }
        }

    }

    /**
     * Beállítja a tápanyag szorzót ehhez a rovarhoz.
     *
     * @param times Az új szorzó értéke.
     */
    public void setNutrientMultiplier(int times) {

        this.nutrientMultiplier = times;

    }

    /**
     * Lehetővé teszi a rovar számára, hogy megegyen egy spórát.
     */
    public void eatSpore() {

        Spore s1 = tecton.removeOldestSpore();

        entomologist.setScore(s1.getNutrientValue());

        if (!(tecton instanceof Orogenix)) {
            s1.takeEffectOn(this);
        }

    }

    /**
     * Engedélyezi a rovar számára az evést.
     */
    public void enableEating() {


        this.canEat = true;


    }

    /**
     * Letiltja a rovar számára az evést.
     */
    public void disableEating() {


        this.canEat = false;


    }

    /**
     * Áthelyezi a rovart a megadott Tecton-ra.
     *
     * @param t A Tecton, ahová a rovart át kell helyezni.
     */
    public void moveTo(Tecton t) {


        t.placeInsect(this);

    }

    /**
     * Levon egy tápanyag pontot a rovarból.
     */
    public void deductNutrientPoint() {

        this.entomologist.setScore(-1);

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

        setNutrientMultiplier(1);

    }

    /**
     * Beállítja a rovar sebességét a megadott értékre.
     *
     * @param speed A rovar új sebessége.
     */
    public void setSpeed(float speed) {

        this.speed = speed;

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

        this.isParalized = true;

    }

    /**
     * Eltávolítja a bénultságot a rovarról.
     */
    public void unParalized() {

        this.isParalized = false;

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

    public String getName() {
        return this.name;
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