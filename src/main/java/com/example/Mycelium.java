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

    private List<Mycelium> connections;

    /**
     * Default constructor.
     */
    public Mycelium() {
        connections = new ArrayList<Mycelium>();
    }

    public boolean canDevelop() { 
        // TODO: Implement this method
        return false;
    }

    public void developMushroomBody() {
        // TODO: Implement this method

    }

    public void enableGrowth() {
        // TODO: Implement this method

    }

    public void createNewBranch(Tecton tecton) {
        Skeleton.logFunctionCall(this, "createNewBranch", tecton);

        Mycelium mycelium;
        if (tecton.mycelia.isEmpty()) {
            mycelium = new Mycelium();
            Skeleton.logCreateInstance(mycelium, "Mycelium", "mycelium");
            tecton.addMycelium(mycelium);
        } else {
            mycelium = tecton.mycelia.getFirst();
        }
        connections.add(mycelium);
        mycelium.connections.add(this);

        Skeleton.logReturn(this, "createNewBranch");
    }

    public void removeConnection(Mycelium with) {
        Skeleton.logFunctionCall(this, "removeConnection", with);

        connections.remove(with);

        Skeleton.logReturn(this, "removeConnection");
    }

    public boolean isConnectedToMushroomBody() {
        // TODO: Implement this method
        return false;
    }

    public void speedUpGrowth() {
        // TODO: Implement this method
    }

    public void resetGrowthSpeed() { 
        // TODO: Implement this method
    }

    public void wither() {
        // TODO: Implement this method
    }

    public List<Mycelium> getConnections() {
        return connections;
    }
}


