package com.example;

import java.util.*;

import static com.example.TectonSize.decreaseSize;

/**
 * A Transix típusú tektonért felel.
 *
 * Ősosztály: Tecton -> Transix
 */
public class Transix extends Tecton {

    public Transix(String name) {
        super(name);
        maxMycelia = 2;
    }

    public Transix(TectonSize size, String name) {
        super(size, name);
        maxMycelia = 2;
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
     * Hozzáadja a tektonhoz a my fonalat.
     *
     * @param mycelium A hozzáadandó mycelium.
     */
    @Override
    public void addMycelium(Mycelium mycelium) {
        this.mycelia.add(mycelium);
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
     * A tekton kettétörését megvalósító metódus. Létrehoz két új tektont
     * egyel kisebb méretkategóriába. Felelős a tekton szomszédainak a két új tekton között
     * való elosztásáért, valamint a ha van rajta gombatest vagy rovar akkor azokat is el kell
     * helyezze az egyik új tektonon.
     *
     * @return A létrejött két új tekton listája.
     */
    @Override
    public List<Tecton> breakApart(String newTectonName1, String newTectonName2) {

        //Két új tekton létrehozása
        Transix t1 = new Transix(decreaseSize(this.size), newTectonName1);
        Transix t2 = new Transix(decreaseSize(this.size), newTectonName2);

        Controller.putToNameMap(t1, newTectonName1);
        Controller.putToNameMap(t2, newTectonName2);

        //Köztük kapcsolat létrehozása
        t1.addTectonToNeighbors(t2);

        if (this.hasInsect()) {
            //Ha van rajta rovar, akkor a szomszédos tektonok közül az egyikre kerül
            if (Controller.isRandomOn()) {
                Random random = new Random();
                int randomIndex = random.nextInt(2);
                if (randomIndex == 0) {
                    for (Insect insect : insects) {
                        t1.placeInsect(insect);
                    }
                } else {
                    for (Insect insect : insects) {
                        t2.placeInsect(insect);
                    }
                }
            } else {
                for (int i = 0; i < insects.size(); i++) {
                    Insect insect = insects.get(i);
                    t1.placeInsect(insect);
                }
            }
        }

        if (this.hasMushroomBody()) {
            //Ha van rajta gombatest, akkor a szomszédos tektonok közül az egyikre kerül
            if (Controller.isRandomOn()) {
                Random random = new Random();
                int randomIndex = random.nextInt(2);
                if (randomIndex == 0) {
                    t1.placeMushroomBody(this.mushroomBody);
                } else {
                    t2.placeMushroomBody(this.mushroomBody);
                }
            } else {
                t2.placeMushroomBody(this.mushroomBody);
            }
        }

        //A két új tektonra kerülnek a myceliumok
        if (!this.mycelia.isEmpty()) {
            for (Mycelium m : this.mycelia) {
                t1.addMycelium(m);
                t2.addMycelium(m);
            }
        }


        //Veszünk egy tectont a szomszédaink közül
        Tecton n1 = this.neighbors.iterator().next();
        this.neighbors.remove(n1);

        //Hozzáadjuk az egyik új tektonhoz
        t1.addTectonToNeighbors(n1);
        n1.changeNeighbour(this, t1);

        Iterator<Tecton> iterator = n1.neighbors.iterator();
        while (iterator.hasNext()) {
            Tecton n2 = iterator.next();
            if (this.neighbors.contains(n2)) {
                this.neighbors.remove(n2);
                t1.addTectonToNeighbors(n2);
                n2.changeNeighbour(this, t2);
            }
        }

        while (!this.neighbors.isEmpty()) {
            Tecton n = this.neighbors.iterator().next();
            this.neighbors.remove(n);
            t2.addTectonToNeighbors(n);
            n.changeNeighbour(this, t2);
        }

        //Később a controllerben a helye
        gameTable.removeTecton(this);
        gameTable.addTecton(t1);
        gameTable.addTecton(t2);

        return new ArrayList<>(Arrays.asList(t1, t2));
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

        if (insect.getTecton() == null){
            insect.setTecton(this);
            this.insects.add(insect);
        } else if (hasConnection(insect)) {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insects.add(insect);
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

