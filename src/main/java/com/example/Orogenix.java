package com.example;

/**
 * A Orogenix típusú tektonért felel.
 *
 * Ősosztály: Tecton -> Orogenix
 */
public class Orogenix extends Tecton {

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
     * Egy igaz-hamis érték, hogy a tektonon elhelyezhető-e
     * gombafonal.
     *
     * @return true, ha elhelyezhető, különben false.
     */
    @Override
    public boolean canAddMycelium() {
        Skeleton.logFunctionCall(this, "canAddMycelium");

        Skeleton.logReturn(this, "canAddMycelium");
        return true;
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
     * Elhelyezi a rovarot a tektonon.
     *
     * Ez a metódus megpróbálja elhelyezni a megadott rovarot a tektonon.
     * Először naplózza a függvényhívást, majd ellenőrzi, hogy a rovar jelenleg nincs-e egy másik tektonon.
     * Ha a rovar nincs egy másik tektonon, akkor beállítja a rovar tektonját erre a tektonra és visszatér.
     * Ha a tekton már tartalmaz egy rovarot, vagy nincs kapcsolat a rovar jelenlegi tektonja és ez a tekton között,
     * akkor a metódus visszatér anélkül, hogy bármit is változtatna.
     * Ha a rovar jelenleg egy másik tektonon van, és van kapcsolat a két tekton között, akkor a metódus
     * semlegesíti a rovaron a tekton hatásait, eltávolítja a rovart a jelenlegi tektonjáról, beállítja a rovar tektonját erre a tektonra,
     * és semlegesíti a rovar spóra hatásait.
     *
     * @param insect A rovar, amit el kell helyezni a tektonon.
     */
    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);

        if (insect.getTecton() == null){
            insect.setTecton(this);
            Skeleton.logReturn(this, "placeInsect");
            return;
        }

        if (this.insect != null || !hasConnection(insect)) {
            Skeleton.logReturn(this, "placeInsect");
        } else {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insect.setTecton(this);
            insect.neutralizeSporeEffects();
            Skeleton.logReturn(this, "placeInsect");
        }

    }
   
}

