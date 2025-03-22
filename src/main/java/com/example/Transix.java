package com.example;

/**
 * Transix is a specific type of Tecton.
 */
public class Transix extends Tecton {

    @Override
    public void placeMushroomBody(MushroomBody mushroomBody) {
       Skeleton.logFunctionCall(this, "placeMushroomBody", mushroomBody);
       this.mushroomBody = mushroomBody;
       Skeleton.logReturn(this, "placeMushroomBody");
    }

    @Override
    public void addMycelium(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "addMycelium", mycelium);
        this.mycelium = mycelium;
        Skeleton.logReturn(this, "addMycelium");
    }

    @Override
    public boolean canAddMycelium() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canAddMycelium'");
    }

    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);
        this.insect = insect;
        insect.setTecton(this);

        Skeleton.logReturn(this, "placeInsect");
    }
   
}

