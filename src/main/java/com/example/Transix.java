package com.example;

/**
 * Transix is a specific type of Tecton.
 */
public class Transix extends Tecton {

    @Override
    public void placeMushroomBody(MushroomBody mushroomBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeMushroomBody'");
    }

    @Override
    public void addMycelium(Mycelium mycelium) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMycelium'");
    }

    @Override
    public boolean canAddMycelium() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canAddMycelium'");
    }

    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);
        
        insect.setTecton(this);

        Skeleton.logReturn(this, "placeInsect");
    }
   
}

