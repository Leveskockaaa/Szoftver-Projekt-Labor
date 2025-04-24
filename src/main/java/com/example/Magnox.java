package com.example;

/**
 * A Magnox típusú tektonért felel.
 *  *
 *  * Ősosztály: Tecton -> Magnox
 */
public class Magnox extends Tecton {

    /**
     * Alapértelmezett konstruktor a Magmox osztályhoz.
     * Beállítja az alapértelmezett értékeket, például a maximális gombafonalak számát.
     */
    public Magnox() {
        super();
        maxMycelia = 1;
    }

    /**
     * Konstruktor, amely beállítja a tekton méretét és az alapértelmezett maximális gombafonalak számát.
     *
     * @param size A tekton mérete.
     */
    public Magnox(TectonSize size) {
        super(size);
        maxMycelia = 1;
    }

    /**
     * Amennyiben nincsen gombatest a
     * tektonon és minden feltétel fennáll elhelyezi a paraaméterként kapott gombatestet a
     * tektonon.
     *
     * @param mushroomBody A gombatest, amit el kell helyezni.
     */
    @Override
    public void placeMushroomBody(MushroomBody mushroomBody) {
        this.mushroomBody = mushroomBody;
    }

    /**
     * Egy igaz-hamis érték, hogy a tektonon elhelyezhető-e
     * gombafonal.
     *
     * @return true, ha elhelyezhető, különben false.
     */
    @Override
    public boolean canAddMycelium() {
        return mycelia.size() < maxMycelia;
    }

    /**
     * Hozzáadja a tektonhoz a my fonalat.
     *
     * @param mycelium A hozzáadandó mycelium.
     */
    @Override
    public void addMycelium(Mycelium mycelium) {
        this.mycelia.add(mycelium);
    }

    /**
     * Elhelyezi a rovarot a tektonon.
     *
     * Ez a metódus megpróbálja elhelyezni a megadott rovarot a tektonon.
     * Először naplózza a függvényhívást, majd ellenőrzi, hogy a rovar jelenleg nincs-e egy másik tektonon.
     * Ha a rovar nincs egy másik tektonon, akkor beállítja a rovar tektonját erre a tektonra és visszatér.
     * Ha a tekton már tartalmaz egy rovarot, vagy nincs kapcsolat a rovar jelenlegi tektonja és ez a tekton között,
     * akkor a metódus visszatér anélkül, hogy bármit is változtatna.
     * Ha a rovar jelenleg egy másik tektonon van, és van kapcsolat a két tekton között, akkor a metódus
     * semlegesíti a rovar tekton hatásait, eltávolítja a rovarot a jelenlegi tektonjáról, beállítja a rovar tektonját erre a tektonra,
     * és levon egy tápanyagértékpontot a rovartól, amit 30 másodpercenként fog majd ismételgetni, amég a rovar a tektonon tartózkodik.
     *
     * @param insect A rovar, amit el kell helyezni a tektonon.
     */
    @Override
    public void placeInsect(Insect insect) {

        if (insect.getTecton() == null){
            insect.setTecton(this);
            this.insects.add(insect);
        } else {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insect.setTecton(this);
            insect.setNutrientMultiplier(2);
        }
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */


    /**
     * Visszaadja a tekton típusát.
     *
     * @return A tekton típusa.
     */
    @Override
    public String printType() {
        return this.getClass().getSimpleName();
    }
}
