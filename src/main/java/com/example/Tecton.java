package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * Az osztály felelőssége egy platformot adni a játékban résztvevő elemeknek, legyenek azok
 * rovarok vagy gombák részei.
 */
public abstract class Tecton {

    /**
     * A tecton eltárolja a szomszédos tectonokat egy halmazban.
     */
    protected HashSet<Tecton> neighbors;

    /**
     * A tekton eltárolja a rajta lévő gombatestet, ha
     * van ilyen.
     */
    protected MushroomBody mushroomBody;

    /**
     * A tecton eltárolja, hogy melyik rovar van éppen rajta egy
     * szimpla egy elemes változóban.
     */
    protected Insect insect;

    /**
     * Egyedi azonosítója a Tecton-nak.
     */
    protected String name;

    /**
     * A négy lehetséges méret közül megmondja, hogy mekkora a tekton.
     */
    protected TectonSize size;

    /**
     * Megmondja, hogy hány gombafonál lehet a tektonon.
     */
    protected int maxMycelia;


    /**
     * A tekton eltárolja a rajta lévő spórákat. Fontos, hogy a
     * FIFO szerűen számontartja a sorrendjüket, hogy egy rovar mindig a legkésőbb érkezőt
     * tudja megenni. Kompozíció, mivel a tekton kettétörésénél a rajta lévő spórák
     * megszűnnek, hiszen ekkor lerázódnak a spórák a tektonról.
     */
    protected List<Spore> spores;

    /**
     * A tecton eltárolja a rajta lévő gombafonalak listáját.
     * Aggregáció, mivel a tekton kettétörésénél nem szűnik meg a gombafonal, sőt kettő
     * lesz belőle is, csak megszakadnak a kapcsolataik.
     */
    protected List<Mycelium> mycelia;

    /**
     * Default Konstruktor.
     */
    public Tecton() {
        spores = new ArrayList<>();
        neighbors = new HashSet<>();
        mushroomBody = null;
        insect = null;
        mycelia = new ArrayList<>();
    }


    /**
     * Visszaadja a szomszédos Tecton-ok halmazát.
     *
     * @return A szomszédos Tecton-ok halmaza.
     */
    public HashSet<Tecton> getNeighbors() {
        return neighbors;
    }

    /**
     * Visszaadja a Tecton-on lévő gombafonalak listáját.
     *
     * @return A gombafonalak listája.
     */
    public List<Mycelium> getMycelia() {
        return mycelia;
    }

    public Insect getInsect() {
        return insect;
    }

    /**
     * A tekton kettétörését megvalósító metódus. Létrehoz két új tektont
     * egyel kisebb méretkategóriába. Felelős a tekton szomszédainak a két új tekton között
     * való elosztásáért, valamint a ha van rajta gombatest vagy rovar akkor azokat is el kell
     * helyezze az egyik új tektonon.
     *
     * @return A létrejött két új tekton listája.
     */
    public List<Tecton> breakApart() {
        Skeleton.logFunctionCall(this, "breakApart");

        Transix t1 = new Transix();
        Skeleton.logCreateInstance(t1, "Transix", "t1");

        Transix t2 = new Transix();
        Skeleton.logCreateInstance(t2, "Transix", "t2");

        Tecton n1 = (Tecton) Skeleton.getFromNameMap("neigh1");
        Tecton n2 = (Tecton) Skeleton.getFromNameMap("neigh2");

        t1.addTectonToNeighbors(t2);

        if (this.hasInsect()) {
            boolean toT1 = Skeleton.logBranch("A t1-re (y), vagy a t2-re (n) kerüljön a rovar?");
            if (toT1) {
                t1.placeInsect(this.insect);
            } else {
                t2.placeInsect(this.insect);
            }
        }

        if (this.hasMushroomBody()) {
            boolean toT1 = Skeleton.logBranch("A t1-re (y), vagy a t2-re (n) kerüljön a gomba test?");
            if (toT1) {
                t1.placeMushroomBody(this.mushroomBody);
                t1.addMycelium(this.mycelia.get(0));
            } else {
                t2.placeMushroomBody(this.mushroomBody);
                t2.addMycelium(this.mycelia.get(0));
            }
        }

        n1.changeNeighbour(this, t2);

        n2.changeNeighbour(this, t1);

        Skeleton.logReturn(this, "breakApart");
        return new ArrayList<>(Arrays.asList(t1, t2));
    }

    /**
     * Hozzáad egy szomszédos Tecton-t ehhez a Tecton-hoz.
     * És a szomszédos Tecton-nak is hozzáadja ezt a Tecton-t.
     *
     * @param tecton A hozzáadandó szomszédos Tecton.
     */
    public void addTectonToNeighbors(Tecton tecton) {
        if (!neighbors.contains(tecton)) {
            Skeleton.logFunctionCall(this, "addTectonToNeighbors", tecton);
            neighbors.add(tecton);
            tecton.addTectonToNeighbors(this);
            Skeleton.logReturn(this, "addTectonToNeighbors");
        }
    }

    /**
     * Megváltoztatja a Tecton egyik szomszédját a kapott Tecton-ra.
     *
     * @param from A régi szomszédos Tecton.
     * @param to Az új szomszédos Tecton.
     */
    public void changeNeighbour(Tecton from, Tecton to) {
        Skeleton.logFunctionCall(this, "changeNeighbour", from, to);
        neighbors.remove(from);
        to.addTectonToNeighbors(this);
        Skeleton.logReturn(this, "changeNeighbour");
    }


    /**
     * Megvizsgálja, hogy van-e gombatest a tektonon.
     *
     * @return true, ha van gombatest, különben false.
     */
    public boolean hasMushroomBody() {
        return mushroomBody != null;
    }

    /**
     * Megvizsgálja, hogy van-e rovar a tektonon.
     *
     * @return true, ha van rovar, különben false.
     */
    public boolean hasInsect() {
        return insect != null;
    }


    /**
     * Átállítja a hasInsect attribútum értékét igazra.
     *
     * @param insect Az új rovar.
     */
    public abstract void placeInsect(Insect insect);


    /**
     * Amennyiben nincsen gombatest a
     * tektonon és minden feltétel fennáll elhelyezi a paraaméterként kapott gombatestet a
     * tektonon.
     *
     * @param mushroomBody A gombatest, amit el kell helyezni.
     */
    public abstract void placeMushroomBody(MushroomBody mushroomBody);

    /**
     * Eltávolítja a tektonon elhelyetkedő gombatestet. Hasznos
     * amikor már elhalt a gombatest.
     *
     * @return A elhalt gombatest.
     */
    public MushroomBody removeMushroomBody() {
        Skeleton.logFunctionCall(this, "removeMushroomBody");

        Skeleton.logReturn(this, "removeMushroomBody");
        return null;
    }

    /**
     * Eltávolítja a tektonon elhelyezkedő rovart.
     */
    public void removeInsect() {
        Skeleton.logFunctionCall(this, "removeInsect");

        insect = null;

        Skeleton.logReturn(this, "removeInsect");
    }

    /**
     * A bemenetként kapott spórát hozzáadja a tektonhoz.
     *
     * @param spore A hozzáadandó spóra.
     */
    public void addSpore(Spore spore) {
        Skeleton.logFunctionCall(this, "addSpore", spore);
        spores.add(spore);
        Skeleton.logReturn(this, "addSpore");
    }


    /**
     * Megmondja, hogy milyen spórák találhatóak a tektonon.
     * 
     * @return A tektonon található spórák listája.
     */
    public List<Spore> sporesAvailable() {
        return spores;
    }

    /**
     * Az s típusú spórából quantity darabot elvesz a
     * tektonról, azaz kiveszi őket a tektonon lévő spórák listájából.
     *
     * @param spore    A spóra, amit el kell venni.
     * @param quantity A spórák száma, amit el kell venni.
     * @return true, ha sikeresen elvette a spórákat, különben false.
     */
    public boolean takeSpore(Spore spore, int quantity) {
        // TODO: Implement logic
        return false;
    }


    /**
     * A tektonon legrégebb óta heverő spórát kiveszi a tektonon lévő
     * spórák listájából.
     *
     * @return A legrégebb óta heverő spóra.
     */
    public Spore removeOldestSpore() {
        Skeleton.logFunctionCall(this, "removeOldestSpore");
        Spore spore = spores.remove(0);
        
        Skeleton.logReturn(this, "removeOldestSpore");
        return spore;
    }

    /**
     * A tektonnal szomszédos tektonok számát adja vissza.
     *
     * @return A szomszédos tektonok száma.
     */
    public int neighborCount() {
        // TODO: Implement logic
        return 0;
    }

    
    /**
     * Egy igaz-hamis érték, hogy a tektonon elhelyezhető-e
     * gombafonal.
     *
     * @return true, ha elhelyezhető, különben false.
     */
    public abstract boolean canAddMycelium();

    /**
     * Hozzáadja a tektonhoz a my fonalat.
     *
     * @param mycelium A hozzáadandó mycelium.
     */
    public abstract void addMycelium(Mycelium mycelium);

    /**
     * Ellenőrzi, hogy van-e kapcsolat a rovar jelenlegi tektonja és a cél tekton között.
     *
     * Ez a metódus megvizsgálja, hogy a megadott rovar jelenlegi tektonja és a cél tekton között
     * van-e kapcsolat. Először ellenőrzi, hogy a rovar jelenlegi tektonja nem null-e.
     * Ha a rovar jelenlegi tektonja null, akkor a metódus hamis értékkel tér vissza.
     * Ha a rovar jelenlegi tektonja nem null, akkor a metódus végigmegy ezen a tektonon lévő
     * gombafonalakon, és minden gombafonal kapcsolatán. Ha talál olyan kapcsolatot, amely
     * a rovar jelenlegi tektonján is megtalálható, akkor a metódus igaz értékkel tér vissza.
     * Ha nem talál ilyen kapcsolatot, akkor a metódus hamis értékkel tér vissza.
     *
     * @param i A rovar, amelynek a tektonját ellenőrizni kell.
     * @return true, ha van kapcsolat a rovar jelenlegi tektonja és a cél tekton között, különben false.
     */
    public boolean hasConnection(Insect i) {
        if (i.getTecton() == null) {
            return false;
        }
        for (Mycelium m : this.mycelia) {
            for (Mycelium con : m.getMyceliumConnections()){
                if (i.getTecton().mycelia.contains(con)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Leveszi a tektonról a rajta lévő my fonalat.
     *
     * @param mycelium A levevendő mycelium.
     * @return true, ha sikeresen levette, különben false.
     */
    public boolean removeMycelium(Mycelium mycelium) {
        // TODO: Implement logic
        return false;
    }

    public String getName() { return this.name; }

}
