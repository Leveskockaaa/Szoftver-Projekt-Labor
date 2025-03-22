package com.example;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents Mycelium in the domain model.
 * Mycelium can be placed on Tectons, chewed by Insects, etc.
 */
public class Mycelium {
    private boolean canGrow;
    private int growthSpeed;

    private final Tecton tecton;
    private Mycologist mycologist;
    private List<Mycelium> myceliumConnections = new ArrayList<Mycelium>();

    /**
     * Default constructor.
     */
    public Mycelium(Tecton tecton) {
        this.canGrow = true;
        this.tecton = tecton;
    }

    public Mycologist getMycologist() {
        return mycologist;
    }

    public List<Mycelium> getMyceliumConnections() {
        return myceliumConnections;
    }

    public boolean canDevelop() {
        Skeleton.logFunctionCall(this, "canDevelop");

        int sporeCount = 0;
        for (Spore s : tecton.sporesAvailable()){
            if(s.getClass() == HypharaSpore.class){ //Spore type?
                sporeCount++;
            }
        }

        Skeleton.logReturn(this, "canDevelop");
        if(sporeCount >= 6 && !tecton.hasMushroomBody()) //Sporecount needed?
            return true;
        return false;

        // Mycelium alosztályok?
        // MyceliumType enum?
        // Mycologiston keresztül?
    }

    public void developMushroomBody() {
        Skeleton.logFunctionCall(this, "developMushroomBody");

        if(canDevelop()) {
            tecton.placeMushroomBody(new Hyphara(tecton));
        }

        Skeleton.logReturn(this, "developMushroomBody");
    }

    public void enableGrowth() {
        Skeleton.logFunctionCall(this, "enableGrowth");
        canGrow = true;
        Skeleton.logReturn(this, "enableGrowth");
    }

    public Mycelium createNewBranch(Tecton tecton) {
        Skeleton.logFunctionCall(this, "createNewBranch", tecton);

        if(tecton.canAddMycelium() && canGrow){
            Mycelium newMycelium = new Mycelium(tecton);
            tecton.addMycelium(newMycelium);
            Skeleton.logCreateInstance(newMycelium, "Mycelium", "newMycelium");
            myceliumConnections.add(newMycelium);
            tecton.addMycelium(newMycelium);
            Skeleton.logReturn(this, "createNewBranch");
            return newMycelium;
        }
        Skeleton.logReturn(this, "createNewBranch");
        return null;
    }

    public void removeConnection(Mycelium with) {
        Skeleton.logFunctionCall(this, "removeConnection", with);

        Skeleton.logReturn(this, "removeConnection");
    }

    public boolean isConnectedToMushroomBody() {
        Skeleton.logFunctionCall(this, "isConnectedToMushroomBody");

        Skeleton.logReturn(this, "isConnectedToMushroomBody");
        return false;
    }

    public void speedUpGrowth() {
        Skeleton.logFunctionCall(this, "speedUpGrowth");

        Skeleton.logReturn(this, "speedUpGrowth");
    }

    public void resetGrowthSpeed() {
        Skeleton.logFunctionCall(this, "resetGrowthSpeed");

        Skeleton.logReturn(this, "resetGrowthSpeed");
    }

    public void wither() {
        Skeleton.logFunctionCall(this, "wither");

        Skeleton.logReturn(this, "wither");
    }
}


