package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.Controller;
import com.example.view.MyceliumView;

/**
 * A gombafonalakat kezelő osztály. Egy-egy fonal egységet valósít meg.
 */
public class Mycelium {

    private String name;

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
     * A gombatesthez tartozó gombász. final mert a gombafonálhoz tartozó
     * gombász nem változhat.
     */
    private final Mycologist mycologist;

    /**
     * Az olyan gombafonalak listája, amelyekkel közvetlen kapcsolatban van a
     * gombafonál. Attól, hogy szomszédos tektonon van egy azonos fajú
     * gombafonál, még nem szomszédosak.
     */
    private final List<Mycelium> myceliumConnections;

    private MyceliumView view;

    private Timer timer;

    /**
     * Konstruktor.
     *
     * @param tecton A tekton, amin a gombafonál elhelyezkedik.
     * @param mycologist A gombafonálhoz tartozó gombász.
     *
     */
    public Mycelium(Tecton tecton, Mycologist mycologist) {
        this.canGrow = true;
        this.insectEaten = false;
        this.growthSpeed = 10;
        this.tecton = tecton;
        this.mycologist = mycologist;
        this.myceliumConnections = new ArrayList<>();
        tecton.addMycelium(this);
        this.view = new MyceliumView(this);
    }

    public MyceliumView getView() {
        return view;
    }

    public String getName() {
        return name;
    }

    /**
     * Getter a gombafonálhoz tartozó tektonhoz.
     *
     * @return A gombafonálhoz tartozó tekton.
     */
    public Tecton getTecton() {
        return tecton;
    }

    /**
     * Getter a gombafonálhoz tartozó gombászhoz.
     *
     * @return A gombafonálhoz tartozó gombász.
     */
    public Mycologist getMycologist() {
        return mycologist;
    }

    /**
     * Getter az olyan gombafonalak listájához, amelyekkel közvetlen kapcsolatban
     * van a gombafonál. Attól, hogy szomszédos tektonon van egy azonos fajú
     * gombafonál, még nem szomszédosak.
     *
     * @return A kapcsolódó gombafonalak listája.
     */
    public List<Mycelium> getMyceliumConnections() {
        return myceliumConnections;
    }

    /**
     * Getter a gombafonálhoz tartozó gombatest típusához. Ezzel a függvénnyel
     * vagyunk képesek megállapítani a gombafonál "faját", míg ezt külön nem
     * tároljuk.
     *
     * @return A gombafonálhoz tartozó gombatest osztály típusa.
     */
    public Class<? extends MushroomBody> getBodyType() {
        return mycologist.getMushroomBodies().get(0).getClass();
    }

    /**
     * Megadja, hogy tud-e gombatestet növeszteni a tektonon fonal.
     *
     * @return true, ha tud gombatestet növeszteni, egyébként false.
     */
    public boolean canDevelop() {
        int sporeCount = 0;
        for (Spore spore : tecton.sporesAvailable()) {
            if (spore.getMushroomBody().getMycologist() == mycologist) {
                sporeCount++;
            }
        }

        return sporeCount >= 6 && !tecton.hasMushroomBody();
    }

    /**
     * Gombatestet fejleszt az adott fonalon és tektonon.
     *
     * @param
     * @return true, ha sikeresen kifejlesztett egy gombatestet, egyébként false.
     */
    public MushroomBody developMushroomBody() {
        if (!canDevelop()) {
            return null;
        }

        MushroomBody mushroomBody = mycologist.createMushroomBody(tecton, name);
        tecton.placeMushroomBody(mushroomBody);
        tecton.takeSpore(mycologist, 6);
        mycologist.addMushroomBody(mushroomBody);

        return mushroomBody;
    }

    /**
     * Engedélyezi a gombatest növesztését.
     */
    public void enableGrowth() {
        System.out.println("Growth enabled");
        canGrow = true;
    }

    public void disableGrowth() {
        System.out.println("Growth disabled");
        canGrow = false;
    }

    /**
     * Először ellenőrzi, hogy képes-e az új tektonra átnőni a gombafonál, majd
     * egy új összeköttetést hoz létre a megadott tektonnal. Ha már volt ott
     * ugyanahhoz a gombászhoz tartozó gombafonál, akkor csak összeköti azokat,
     * ha nem, akkor újat hoz létre ott.
     *
     * @param tecton A tekton, amire át akarunk nőni.
     * @return Ha sikerült átnőni, akkor az új gombafonál referenciája,
     * egyébként null.
     */
    public Mycelium createNewBranch(Tecton tecton) {
        try {
            if (!canGrow && Controller.isRandomOn()) {
                throw new IllegalArgumentException("Cannot grow");
            }
            for (Mycelium mycelium : myceliumConnections) {
                if (mycelium.getTecton() == tecton) {
                    throw new IllegalArgumentException("Already connected to this tecton");
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }

        for (Mycelium mycelium : tecton.getMycelia()) {
            if (mycelium.getMycologist() == mycologist) {
                try {
                    this.addConnection(mycelium);
                } catch (IllegalArgumentException exception) {
                    System.out.println(exception.getMessage());
                    throw new IllegalArgumentException(exception.getMessage());
                }
                return null;
            }
        }

        if (tecton.canAddMycelium()) {
            Mycelium newMycelium = new Mycelium(tecton, mycologist);

            try {
                this.addConnection(newMycelium);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                throw new IllegalArgumentException(exception.getMessage());
            }

            return newMycelium;
        } else {

        }

        return null;
    }

    /**
     * A bemenetként kapott gombafonalat kapcsolja össze önmagával.
     *
     * @param mycelium A gombafonal, amivel kapcsolatba lépünk.
     */
    public void addConnection(Mycelium mycelium) {
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
//        this.canGrow = false;
//        mycelium.canGrow = false;
    }

    /**
     * A megadott gombafonallal megszakítja a kapcsolatot. Kiveszi a kapcsolatban
     * lévő fonalak listájából a megadott gombafonalat.
     *
     * @param mycelium A gombafonal, amivel meg akarjuk szakítani a kapcsolatot.
     */
    public void removeConnection(Mycelium mycelium) {
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
    }

    /**
     * Megadja, hogy a fonal kapcsolódik-e gombatesthez.
     *
     * @return true, ha kapcsolódik gombatesthez, egyébként false.
     */
    public boolean isConnectedToMushroomBody() {
        HashSet<Mycelium> visitedMycelia = new HashSet<>();
        LinkedList<Mycelium> queue = new LinkedList<>();
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
     * Felgyorsítja a növekedést, azaz csökkenti a szükséges időt, aminek két
     * növekedés között el kell telnie.
     *
     * @param time Az az idő, amivel csökkenteni szeretnénk a növekedési időt.
     */
    public void speedUpGrowth(float time) {
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
    }

    /**
     * Visszaállítja a növekedési sebességet az eredetire.
     */
    public void resetGrowthSpeed() {
        growthSpeed = 10;
    }

    /**
     * Hatására a fonal sorvadni kezd. Ha adott időn belül nem kötik újra
     * gombatesthez, akkor a fonal eltűnik.
     */
    public void wither() {
        if (isWithering()) return;

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tecton.removeMycelium(Mycelium.this);
                timer = null;
            }
        };
        timer.schedule(task, 15000);
    }

    public boolean isWithering() {
        return timer != null;
    }

    public void cancelWither() {
        if (timer == null) return;
        timer.cancel();
        timer.purge();
        timer = null;
    }

    public float getChewDelay() {
        // kéne egy metódus a MushroomBody-ba ami ezt visszaadja
        // ezeket az értékeket a MushroomBody leszármazottaknak egy statikus változóban
        // kell tárolniuk
        // return mushroomBodyType.getChewDelay();

        // vagy, ami szerintem objektum orientáltabb lenne az az, hogy a Mycologist eltárolja
        // a gombafajához tartozó statikus alap értéket, amit akkor inicializál magában (Mycologist), amikor
        // létrehozza az első gombatestjét, ami biztosan lesz, mert a játék elején ez biztosan megtörténik
        // így akkor is lekérdezhető lesz a fajhoz tartozó érték ha éppen nincs gombatestje sem
        // és akkor itt se kell használni ezt a csúnya Class getter-t

        return 0.0f;
    }

    /**
     * Rovart eszik a gombafonal.
     */
    public Insect eatInsect() {
        if (insectEaten || !tecton.hasInsect()) {
            return null;
        }

        Insect insect = tecton.getInsects().get(0);

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
        insect.getEntomologist().removeInsect(insect);

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

        // TODO: a név bemenetet meghatározni valahogy
        MushroomBody mushroomBody = mycologist.createMushroomBody(tecton, "name");
        tecton.placeMushroomBody(mushroomBody);
        insectEaten = true;
        return insect;
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    /**
     * Visszaadja a gombafonál nevét.
     *
     * @return A gombafonál neve.
     */
    public String printName() {
        return name;
    }

    /**
     * Visszaadja, hogy a gombafonál képes-e növekedni.
     *
     * @return "Yes", ha képes növekedni, különben "No".
     */
    public String printCanGrow() {
        return canGrow ? "Yes" : "No";
    }

    /**
     * Visszaadja a gombafonál növekedési sebességét.
     *
     * @return A növekedési sebesség szöveges formában.
     */
    public String printGrowthSpeed() {
        return String.valueOf(growthSpeed);
    }

    /**
     * Visszaadja a mycelium kapcsolatok nevét egy formázott szövegként.
     * A kapcsolatok nevei egy listában jelennek meg, vesszővel elválasztva.
     * Ha nincsenek kapcsolatok, akkor egy üres lista ("[]") kerül visszaadásra.
     *
     * @return A kapcsolatok neveit tartalmazó szöveg formázott listaként.
     */
    public String printConnections() {
        StringBuilder connections = new StringBuilder();
        connections.append("[");
        for (Mycelium mycelium : myceliumConnections) {
            connections.append(mycelium.printName()).append(", ");
        }
        if (connections.length() > 1) {
            connections.setLength(connections.length() - 2); // remove last comma and space
        }
        connections.append("]");
        return connections.toString();
    }

    public String printMushroomBodys() {
        return "TODO";
    }
}
