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
    private final List<Mycelium> myceliumConnections;

    /**
     * Default constructor.
     */
    public Mycelium(Tecton tecton) {
        this.growthSpeed = 0;
        this.tecton = tecton;
        this.mycologist = null;
        myceliumConnections = new ArrayList<Mycelium>();
    }

    public Mycologist getMycologist() {
        return mycologist;
    }

    public void setMycologist(Mycologist mycologist) {
        this.mycologist = mycologist;
    }

    public List<Mycelium> getMyceliumConnections() {
        return myceliumConnections;
    }

    public Class<? extends MushroomBody> getBodyType(){
        return mycologist.getMushroomBodies().getFirst().getClass();
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
        if(sporeCount >= 6 && !tecton.hasMushroomBody())
            return true;
        return false;
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

    public void removeConnection(Mycelium with) {
        Skeleton.logFunctionCall(this, "removeConnection", with);

        myceliumConnections.remove(with);

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


