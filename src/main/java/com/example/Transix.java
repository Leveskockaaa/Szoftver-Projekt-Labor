package com.example;

/**
 * A Transix típusú tektonért felel.
 *
 * Ősosztály: Tecton -> Transix
 */
public class Transix extends Tecton {

    public Transix() {
        super();
    }

    public Transix(TectonSize size) {
        super(size);
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
       Skeleton.logFunctionCall(this, "placeMushroomBody", mushroomBody);
       this.mushroomBody = mushroomBody;
       Skeleton.logReturn(this, "placeMushroomBody");
    }

    /**
     * Hozzáadja a tektonhoz a my fonalat.
     *
     * @param mycelium A hozzáadandó mycelium.
     */
    @Override
    public void addMycelium(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "addMycelium", mycelium);
        this.mycelia.add(mycelium);
        Skeleton.logReturn(this, "addMycelium");
    }

    /**
     * Egy igaz-hamis érték, hogy a tektonon elhelyezhető-e
     * gombafonal.
     *
     * @return true, ha elhelyezhető, különben false.
     */
    @Override
    public boolean canAddMycelium() {
        if (mycelia.size() < maxMycelia) {
            Skeleton.logFunctionCall(this, "canAddMycelium");

            Skeleton.logReturn(this, "canAddMycelium");
            return true;
        }
        return false;
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
     * és a duplájára növeli a tápanyagérték szorzót.
     *
     * @param insect A rovar, amit el kell helyezni a tektonon.
     */
    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);

        if (insect.getTecton() == null){
            insect.setTecton(this);
            this.insect = insect;
            Skeleton.logReturn(this, "placeInsect");
            return;
        }

        if (this.insect != null || !hasConnection(insect)) {
            Skeleton.logReturn(this, "placeInsect");
        } else {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insect.setTecton(this);
            insect.setNutrientMultiplier(2);
            Skeleton.logReturn(this, "placeInsect");
        }
    }
   
}

