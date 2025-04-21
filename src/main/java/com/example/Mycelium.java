package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * A gombafonalakat kezelő osztály. Egy-egy fonal egységet valósít meg.
 */
public class Mycelium {
    /**
     * Egy igaz-hamis érték arról, hogy éppen tud-e növekedni a gombafonal.
     */
    private boolean canGrow;

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
     */
    private Mycologist mycologist;

    /**
     * Az olyan gombafonalak listája amelyekkel közvetlen kapcsolatban van a gombafonál.
     * Attól, hogy szomszédos tektonon van egy azonos fajú gombafonál még nem szomszédosak.
     */
    private final List<Mycelium> myceliumConnections;

    /**
     * Default constructor.
     */
    public Mycelium(Tecton tecton) {
        this.growthSpeed = 10;
        this.tecton = tecton;
        this.mycologist = null;
        myceliumConnections = new ArrayList<Mycelium>();
    }

    /**
     * Getter a gobmafonálhoz tartozó gombászhoz.
     * @return A gombafonalhoz tartozó gombász.
     */
    public Mycologist getMycologist() {
        return mycologist;
    }

    /**
     * Getter a gobmafonálhoz tartozó tektonhoz.
     * @return A gombafonalhoz tartozó tekton.
     */
    public Tecton getTecton() {
        return tecton;
    }

    /**
     * Setter a gobmafonálhoz tartozó gombászhoz.
     * @param mycologist Az új gobmafonálhoz tartozó gombász.
     */
    public void setMycologist(Mycologist mycologist) {
        this.mycologist = mycologist;
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

        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == HypharaSpore.class){ //Spore type?
                sporeCount++;
            }
        }

        Skeleton.logReturn(this, "canDevelop");
        if(sporeCount >= 6 && !tecton.hasMushroomBody())
            return true;
        return false;
    }

    /**
     * Gombatestet fejleszt az adott fonalon és tektonon.
     */
    public void developMushroomBody() {
        Skeleton.logFunctionCall(this, "developMushroomBody");

        if(canDevelop()) {
            tecton.placeMushroomBody(new Hyphara(tecton));
        }

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
     * egy új összeköttetést hoz létre t tektonnal. Ha már volt ott ugyanahoz a gombászhoz
     * tartozó gombafonál, akkor csak összeköti azokat, ha nem akkor újat hoz létre ott.
     * @param tecton A tekton amire át akarunk nőni.
     * @return Ha sikerült átnőni, akkor az új gombafonál referenciája, egyébként null.
     */
    public Mycelium createNewBranch(Tecton tecton) {
        Skeleton.logFunctionCall(this, "createNewBranch", tecton);

        if(canGrow){
            if(tecton.canAddMycelium()){
                Mycelium newMycelium = new Mycelium(tecton);
                Skeleton.logCreateInstance(newMycelium, "Mycelium", "newMycelium");
                newMycelium.setMycologist(this.mycologist);

                tecton.addMycelium(newMycelium);
                myceliumConnections.add(newMycelium);
                newMycelium.myceliumConnections.add(this);

                Skeleton.logReturn(this, "createNewBranch");
                return newMycelium;
            }
            else{
                for(Mycelium mycelium : tecton.getMycelia()){
                    if(mycelium.getBodyType() == getBodyType()){
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
     * my gombafonallal megszakítja a kapcsolatot.
     * Kiveszi a kapcsolatban lévő fonalak listájából my-t.
     * @param my A gombafonal amivel meg akarjuk szakítani a kapcsolatot.
     */
    public void removeConnection(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "removeConnection", mycelium);

        myceliumConnections.remove(mycelium);

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
     */
    public void speedUpGrowth(float time) {
        Skeleton.logFunctionCall(this, "speedUpGrowth");

        growthSpeed -= time;
        if(growthSpeed < 0) {
            growthSpeed = 0;
        }

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
        
        Skeleton.logReturn(this, "wither");
    }
}


