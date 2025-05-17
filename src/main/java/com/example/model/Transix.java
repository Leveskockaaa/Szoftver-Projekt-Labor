package com.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.example.Controller;
import static com.example.model.TectonSize.decreaseSize;

/**
 * A Transix típusú tektonért felel.
 *
 * Ősosztály: Tecton -> Transix
 */
public class Transix extends Tecton {

    /**
     * Létrehoz egy új Transix objektumot a megadott névvel.
     *
     * @param name A Transix neve.
     */
    public Transix() {
        super();
        maxMycelia = 2;
    }

    /**
     * Létrehoz egy új Transix objektumot a megadott méretkategóriával és névvel.
     *
     * @param size A Transix méretkategóriája.
     * @param name A Transix neve.
     */
    public Transix(TectonSize size) {
        super(size);
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
        System.out.println("Transix placeMushroomBody() called");
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
     * A tekton kettétörését megvalósító metódus. Létrehoz
     * két új tektont egyel kisebb méretkategóriába. Felelős a tekton szomszédainak a két
     * új tekton között való elosztásáért, valamint a, ha van rajta gombatest vagy rovar
     * akkor azokat is el kell helyezze az egyik új tektonon. Ezt úgy valósítja meg, hogy
     * veszi azt eredeti (t) tekton szomszédjainak listáját, majd ebből kiveszi a lista első
     * elemét, hozzáadja az egyik létrejövő (t1) tekton szomszédjaihoz. Ezután a t1
     * tekton listájához utoljára hozáadott tekton szomszédjai között keres olyat, ami még
     * benne van a t szomszédai között, kiveszi az első ilyet a t szomszédai közül és
     * hozzáadja t1 szomszédaihoz. Ezt addig ismétli amíg a t eredeti szomszédainak
     * hozzának (kb.) felét eléri. Ezután a maradék tektonokat a t listájából átrakja a
     * másik létrejövő tekton (t2) listájába. Ha van rajta Mycelium akkor azt átrakja mind
     * a két új tectonra. Visszaadja a két létrejött tectont
     *
     * @return A létrejött két új tekton listája.
     */
    @Override
    public List<Tecton> breakApart() {
        System.out.println("Transix breakApart() called");

        //Két új tekton létrehozása
        Transix t1 = new Transix(decreaseSize(this.size));
        Transix t2 = new Transix(decreaseSize(this.size));

        //Köztük kapcsolat létrehozása
        t1.addTectonToNeighbors(t2);

        if (this.hasInsect()) {
            //Ha van rajta rovar, akkor a szomszédos tektonok közül az egyikre kerül
            if (Controller.isRandomOn()) {
                Random random = new Random();
                int randomIndex = random.nextInt(2);
                if (randomIndex == 0) {
                    t1.setInsects(insects);
                } else {
                    t2.setInsects(insects);
                }
            } else {
                t1.setInsects(insects);
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

        for (Iterator<Tecton> it = n1.neighbors.iterator(); it.hasNext(); ) {
            Tecton n2 = it.next();
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

//        //Később a controllerben a helye
//        gameTable.removeTecton(this);
//        gameTable.addTecton(t1);
//        gameTable.addTecton(t2);

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

