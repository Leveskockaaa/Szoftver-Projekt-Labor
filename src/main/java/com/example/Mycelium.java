package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * A gombafonalakat kezelő osztály. Egy-egy fonal egységet valósít meg.
 */
public class Mycelium {
    /**
     * Egy igaz-hamis érték arról, hogy éppen tud-e növekedni a gombafonal.
     */
    private boolean canGrow;

    /**
     * Egy igaz-hamis érték arról, hogy a gombafonál megevett-e egy rovart.
     */
    private boolean insectEaten;

    /**
     * Egy időtartam, amelynek leteltekor a fonal újra növekedhet.
     */
    private float growthSpeed;

    /**
     * A tekton amin a gombafonál elhelyezkedik.
     */
    private final Tecton tecton;

    /**
     * A gombatesthez tartozó gombász.
     * final mert a gombafonálhoz tartozó gombász nem változhat
     */
    private final Mycologist mycologist;

    /**
     * Az olyan gombafonalak listája amelyekkel közvetlen kapcsolatban van a gombafonál.
     * Attól, hogy szomszédos tektonon van egy azonos fajú gombafonál még nem szomszédosak.
     */
    private final List<Mycelium> myceliumConnections;

    /**
     * Konstruktor.
     * @param tecton A tekton amin a gombafonál elhelyezkedik.
     * @param mycologist A gombafonálhoz tartozó gombász.
     */
    public Mycelium(Tecton tecton, Mycologist mycologist) {
        this.canGrow = true;
        this.insectEaten = false;
        this.growthSpeed = 10;
        this.tecton = tecton;
        this.mycologist = mycologist;
        this.myceliumConnections = new ArrayList<Mycelium>();
    }

    /**
     * Getter a gobmafonálhoz tartozó tektonhoz.
     * @return A gombafonalhoz tartozó tekton.
     */
    public Tecton getTecton() {
        return tecton;
    }

    /**
     * Getter a gobmafonálhoz tartozó gombászhoz.
     * @return A gombafonalhoz tartozó gombász.
     */
    public Mycologist getMycologist() {
        return mycologist;
    }

    /**
     * Getter az olyan gombafonalak listájához amelyekkel közvetlen kapcsolatban van a gombafonál.
     * Attól, hogy szomszédos tektonon van egy azonos fajú gombafonál még nem szomszédosak.
     * @return A kapcsolódó gombafonalak listája.
     */
    public List<Mycelium> getMyceliumConnections() {
        return myceliumConnections;
    }

    /**
     * Getter a gombafonálhoz tartozó gombatest típusához. Ezzel a függvénnyel vagyunk képesek
     * megállapítani a gombafonál "faját", míg ezt külön nem tároljuk.
     * @return A gombafonálhoz tartozó gombatest osztály típusa.
     */
    public Class<? extends MushroomBody> getBodyType(){
        return mycologist.getMushroomBodies().get(0).getClass();
    }

    /**
     * Megadja, hogy tud-e gombatestet növeszteni a tektonon fonal.
     * @return true, ha tud gombatestet növeszteni, egyébként false.
     */
    public boolean canDevelop() {
        Skeleton.logFunctionCall(this, "canDevelop");

        // muszáj, hogy egy adott faj spórái legyenek a tektonon?
        // mert ha nem akkor rögtön vissza lehetne térni azzal, hogy
        // a tecton.sporesAvailable().size() nagyobb-egyenlő-e, mint 6

        int sporeCount = 0;
        for (Spore spore : tecton.sporesAvailable()) {
            if(spore.getClass() == HypharaSpore.class) {
                sporeCount++;
            }
        }
        
        Skeleton.logReturn(this, "canDevelop");

        return sporeCount >= 6 && !tecton.hasMushroomBody();
    }

    /**
     * Gombatestet fejleszt az adott fonalon és tektonon.
     */
    public void developMushroomBody() {
        Skeleton.logFunctionCall(this, "developMushroomBody");

        try {
            if (!canDevelop()) {
                throw new IllegalArgumentException("Cannot develop mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        try {
            // ellenőrzés itt vagy hívjuk hozzuk létre az új gombatestet és
            // hívjuk meg a tecont-on a placeMushroomBody() metódust és ha már van
            // akkor ott dobunk exception-t?
            // viszont így feleslegesen hozzuk létre a gombatestet ha true a hasMushroomBody()
            // legyen a mushroomBody létrehozása is már a tecton-on?
            // de akkor nem kell bemeneti paraméter!
            if (tecton.hasMushroomBody()) {
                throw new IllegalArgumentException("Tecton already has a mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        MushroomBody mushroomBody = MushroomBody.createRandomMushroomBody(tecton, mycologist);
        tecton.placeMushroomBody(mushroomBody);

        Skeleton.logReturn(this, "developMushroomBody");
    }

    /**
     * Engedélyezi a gombatest növesztését.
     */
    public void enableGrowth() {
        Skeleton.logFunctionCall(this, "enableGrowth");
        canGrow = true;
        Skeleton.logReturn(this, "enableGrowth");
    }

    /**
     * Először ellenőrzi, hogy képes-e az új tektonra átnőni a gombafonál, majd
     * egy új összeköttetést hoz létre tecton tektonnal. Ha már volt ott ugyanahoz a gombászhoz
     * tartozó gombafonál, akkor csak összeköti azokat, ha nem akkor újat hoz létre ott.
     * @param tecton A tekton amire át akarunk nőni.
     * @return Ha sikerült átnőni, akkor az új gombafonál referenciája, egyébként null.
     */
    public Mycelium createNewBranch(Tecton tecton) {
        Skeleton.logFunctionCall(this, "createNewBranch", tecton);

        if (canGrow) {
            if(tecton.canAddMycelium()) {
                Mycelium newMycelium = new Mycelium(tecton, mycologist);

                Skeleton.logCreateInstance(newMycelium, "Mycelium", "newMycelium");

                tecton.addMycelium(newMycelium);
                this.addConnection(newMycelium);

                Skeleton.logReturn(this, "createNewBranch");
                return newMycelium;
            }
            else {
                for (Mycelium mycelium : tecton.getMycelia()) {
                    if (mycelium.getBodyType() == getBodyType()) {
                        myceliumConnections.add(mycelium);
                        mycelium.myceliumConnections.add(this);
                        break;
                    }
                }
            }
        }

        Skeleton.logReturn(this, "createNewBranch");
        return null;
    }

    /**
     * A bemenetként kapott mycelium gombafonanalat kapcsolja össze önamgával. 
     * @param mycelium A gombafonal amivel kapcsolatba lépünk.
     */
    public void addConnection(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "addConnection", mycelium);

        try {
            if (mycelium == null) {
                throw new IllegalArgumentException("Mycelium cannot be null");
            }
            if (mycelium.getMycologist() != mycologist) {
                throw new IllegalArgumentException("Mycelium must belong to the same Mycologist");
            }
            if (mycelium == this) {
                throw new IllegalArgumentException("Cannot add connection to itself");
            }
            if (!mycelium.getTecton().getNeighbors().contains(tecton)) {
                throw new IllegalArgumentException("Mycelium must be on a neighboring Tecton");
            }
            if (myceliumConnections.contains(mycelium)) {
                throw new IllegalArgumentException("Connection already exists");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        myceliumConnections.add(mycelium);
        mycelium.myceliumConnections.add(this);

        Skeleton.logReturn(this, "addConnection");
    }

    /**
     * my gombafonallal megszakítja a kapcsolatot.
     * Kiveszi a kapcsolatban lévő fonalak listájából my-t.
     * @param mycelium A gombafonal amivel meg akarjuk szakítani a kapcsolatot.
     */
    public void removeConnection(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "removeConnection", mycelium);

        try {
            if (mycelium == null) {
                throw new IllegalArgumentException("Mycelium cannot be null");
            }
            if (mycelium.getMycologist() != mycologist) {
                throw new IllegalArgumentException("Mycelium must belong to the same Mycologist");
            }
            if (mycelium == this) {
                throw new IllegalArgumentException("Cannot remove connection to itself");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        myceliumConnections.remove(mycelium);
        mycelium.myceliumConnections.remove(this);

        Skeleton.logReturn(this, "removeConnection");
    }

    /**
     * Megadja, hogy a fonal kapcsolódik-e gombatesthez.
     * @return true, ha kapcsolódik gombatesthez, egyébként false.
     */
    public boolean isConnectedToMushroomBody() {
        HashSet<Mycelium> visitedMycelia = new HashSet<Mycelium>();
        LinkedList<Mycelium> queue = new LinkedList<Mycelium>();
        queue.add(this);
        visitedMycelia.add(this);
        
        while (!queue.isEmpty()) {
            Mycelium current = queue.poll();
            
            Tecton currentTecton = current.getTecton();
            Mycologist thisMycologist = this.getMycologist();
            for (MushroomBody body : thisMycologist.getMushroomBodies()) {
                if (body.getTecton() == currentTecton) {
                    return true;
                }
            }
            
            for (Mycelium neighbor : current.getMyceliumConnections()) {
                if (!visitedMycelia.contains(neighbor) && neighbor.getMycologist() == mycologist) {
                    visitedMycelia.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }

    /**
     * Felgyorsítja a növekedést, azaz csökkenti a szükséges időt,
     * aminek két növekedést között el kell telnie.
     * @param time Az az idő, amivel csökkenteni szeretnénk a növekedési időt.
     */
    public void speedUpGrowth(float time) {
        Skeleton.logFunctionCall(this, "speedUpGrowth");

        try {
            if (time <= 0) {
                throw new IllegalArgumentException("Time cannot be negative or zero");
            }
            if (growthSpeed - time < 0) {
                throw new IllegalArgumentException("Growth speed cannot be negative");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        growthSpeed -= time;

        Skeleton.logReturn(this, "speedUpGrowth");
    }

    /**
     * Visszaállítja a növekedési sebességet az eredetire.
     */
    public void resetGrowthSpeed() {
        Skeleton.logFunctionCall(this, "resetGrowthSpeed");

        growthSpeed = 10;

        Skeleton.logReturn(this, "resetGrowthSpeed");
    }

    /**
     * Hatására a fonal sorvadni kezd. Ha adott időn belül nem kötik újra
     * gombatesthez, akkor a fonal eltűnik.
     */
    public void wither() {
        Skeleton.logFunctionCall(this, "wither");
        
        // egy új timer pélány indítása, ami elkezdi visszafelé számolni az időt
        // vagy 0-tól a megadott ideig és ha végetért a timer akkor eltűnik a fonál
        // és megszűnik a timer példány

        // timer példány tárolása minden gombafonalhoz?
        // végigmenni az összes elérhető gombafonálon mindegyikhez új timer?
        // természetesen csak akkor ha a fonal nem kapcsolódik gombatesthez

        // ha ezzel a fonállal kapcsolat jön létre akkor az kivált egy eseményt
        // ami megnézi, hogy kapcsolódik-e gombatesthez és ha igen akkor megszünteti
        // a timer példányt és végigmegy a kapcsolódó gombafonálakon és megszünteti
        // a timer példányokat

        Skeleton.logReturn(this, "wither");
    }

    public float getChewDelay() {
        Class<? extends MushroomBody> mushroomBodyType = getBodyType();

        // kéne egy metódus a MushroomBody-ba ami ezt visszaadja
        // ezeket az értékeket a MushroomBody leszármazottaknak egy statikus változóban
        // kell tárolniuk
        // return mushroomBodyType.getChewDelay();

        return 0.0f;
    }

    public void eatInsect() {
        try {
            if (insectEaten) {
                throw new IllegalArgumentException("Insect already eaten");
            }
            // ellenőrzés itt vagy meg a removeInsect() metódust és ha nincs
            // insect a tecton-on akkor ott dobunk exception-t?
            if (!tecton.hasInsect()) {
                throw new IllegalArgumentException("No insect to eat");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

       Insect insect = tecton.getInsect();

       try {
            if (insect == null) {
                throw new IllegalArgumentException("Insect is null");
            }
            // ellenőrzés itt vagy hívjuk meg tecton-on a removeInsect() metódust
            // és ha az isParalized() false akkor ott dobunk exception-t?
            if (!insect.isParalized()) {
                throw new IllegalArgumentException("Insect is not paralyzed");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        tecton.removeInsect();

        try {
            // ellenőrzés itt vagy hívjuk hozzuk létre az új gombatestet és
            // hívjuk meg a tecont-on a placeMushroomBody() metódust és ha már van
            // akkor ott dobunk exception-t?
            // viszont így feleslegesen hozzuk létre a gombatestet ha true a hasMushroomBody()
            // legyen a mushroomBody létrehozása is már a tecton-on?
            // de akkor nem kell bemeneti paraméter!
            if (tecton.hasMushroomBody()) {
                throw new IllegalArgumentException("Tecton already has a mushroom body");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        MushroomBody mushroomBody = MushroomBody.createRandomMushroomBody(tecton, mycologist);
        tecton.placeMushroomBody(mushroomBody);
        insectEaten = true;
    }
}


