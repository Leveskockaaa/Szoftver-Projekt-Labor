package com.example;

/**
 * Transix is a specific type of Tecton.
 */
public class Transix extends Tecton {

    @Override
    public void placeMushroomBody(MushroomBody mushroomBody) {
        Skeleton.logFunctionCall(this, "placeMushroomBody", mushroomBody);

        Skeleton.logReturn(this, "placeMushroomBody");
    }

    @Override
    public void addMycelium(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "addMycelium", mycelium);

        Skeleton.logReturn(this, "addMycelium");
    }

    @Override
    public boolean canAddMycelium() {
        Skeleton.logFunctionCall(this, "canAddMycelium");

        Skeleton.logReturn(this, "canAddMycelium");
        return true;
    }

    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);
        
        insect.setTecton(this);

        Skeleton.logReturn(this, "placeInsect");
    }
   
}

