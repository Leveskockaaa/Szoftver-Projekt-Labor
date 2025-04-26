package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    protected List<Insect> insects = new ArrayList<Insect>();

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
    public Tecton(String name) {
        this.name = name;
        size = TectonSize.GIANT;
        spores = new ArrayList<>();
        neighbors = new HashSet<>();
        mushroomBody = null;
        insects = new ArrayList<>();
        mycelia = new ArrayList<>();
    }

    public Tecton(TectonSize size, String name) {
        this.name = name;
        this.size = size;
        spores = new ArrayList<>();
        neighbors = new HashSet<>();
        mushroomBody = null;
        insects = null;
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

    public List<Insect> getInsects() {
        return insects;
    }

    /**
     * A tekton kettétörését megvalósító metódus. Létrehoz két új tektont
     * egyel kisebb méretkategóriába. Felelős a tekton szomszédainak a két új tekton között
     * való elosztásáért, valamint a ha van rajta gombatest vagy rovar akkor azokat is el kell
     * helyezze az egyik új tektonon.
     *
     * @return A létrejött két új tekton listája.
     */
    public abstract List<Tecton> breakApart(String newTectonName1, String newTectonName2);
    
    /**
     * Hozzáad egy szomszédos Tecton-t ehhez a Tecton-hoz.
     * És a szomszédos Tecton-nak is hozzáadja ezt a Tecton-t.
     *
     * @param tecton A hozzáadandó szomszédos Tecton.
     */
    public void addTectonToNeighbors(Tecton tecton) {
        if (!neighbors.contains(tecton)) {
            neighbors.add(tecton);
            tecton.addTectonToNeighbors(this);
        }
    }

    /**
     * Megváltoztatja a Tecton egyik szomszédját a kapott Tecton-ra.
     *
     * @param from A régi szomszédos Tecton.
     * @param to Az új szomszédos Tecton.
     */
    public void changeNeighbour(Tecton from, Tecton to) {
        neighbors.remove(from);
        to.addTectonToNeighbors(this);
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
        return insects != null && !insects.isEmpty();
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
        MushroomBody mushroomBody = this.mushroomBody;
        this.mushroomBody = null;
        return mushroomBody;
    }

    /**
     * Eltávolítja a tektonon elhelyezkedő rovart.
     */
    public void removeInsect() {
        this.insects.remove(this.insects.get(0));
    }

    /**
     * A bemenetként kapott spórát hozzáadja a tektonhoz.
     *
     * @param spore A hozzáadandó spóra.
     */
    public void addSpore(Spore spore) {
        spores.add(spore);
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
        int count = 0;
        for (Spore currentSpore : spores) {
            if (currentSpore.getClass().equals(spore.getClass())) {
                count++;
            }
        }
        if (count < quantity) {
            return false;
        }
        int removedCount = 0;
        for (Iterator<Spore> iterator = spores.iterator(); iterator.hasNext() && removedCount < quantity; ) {
            Spore currentSpore = iterator.next();
            if (currentSpore.getClass().equals(spore.getClass())) {
                iterator.remove();
                removedCount++;
            }
        }
        return true;
    }


    /**
     * A tektonon legrégebb óta heverő spórát kiveszi a tektonon lévő
     * spórák listájából.
     *
     * @return A legrégebb óta heverő spóra.
     */
    public Spore removeOldestSpore() {
        return spores.remove(0);
    }

    /**
     * A tektonnal szomszédos tektonok számát adja vissza.
     *
     * @return A szomszédos tektonok száma.
     */
    public int neighborCount() {
        return neighbors.size();
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
        if (mycelium != null && mycelia.contains(mycelium)) {
            mycelia.remove(mycelium);
            return true;
        }
        return false;
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printSize() {
        return size.toString();
    }

    public String printMaxMycelia() {
        return String.valueOf(maxMycelia);
    }

    public String printName() {
        return name;
    }

    public String printNeighbors() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Tecton t : neighbors) {
            sb.append(t.printName()).append(": ").append(t.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }

    public abstract String printType();

    public String printMushroomBody() {
        if (mushroomBody != null) {
            return mushroomBody.printName() + ": " + mushroomBody.printType();
        } else {
            return "-";
        }
    }

    public String printMycelia() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Mycelium m : mycelia) {
            sb.append(m.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }

    public String printSpores() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Spore s : spores) {
            sb.append(s.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }

    public String printInsects() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Insect i : insects) {
            sb.append(i.printName()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // remove the last comma and space
        }
        sb.append("]");
        return sb.toString();
    }
    public String getName() { return this.name; }

}
