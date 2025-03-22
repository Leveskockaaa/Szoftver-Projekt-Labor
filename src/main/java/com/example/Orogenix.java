package com.example;

/**
 * Oregenix is a specific type of Tecton.
 */
public class Orogenix extends Tecton {

    @Override
    public void placeMushroomBody(MushroomBody mushroomBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeMushroomBody'");
    }

    @Override
    public boolean canAddMycelium() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canAddMycelium'");
    }

    @Override
    public void addMycelium(Mycelium mycelium) {
        Skeleton.logFunctionCall(this, "addMycelium", mycelium);
        this.mycelia.add(mycelium);
        Skeleton.logReturn(this, "addMycelium");
    }

    @Override
    public void placeInsect(Insect insect) {
        Skeleton.logFunctionCall(this, "placeInsect", insect);

        if (insect.getTecton() == null){
            insect.setTecton(this);
            Skeleton.logReturn(this, "placeInsect");
            return;
        }

        if (this.insect != null || !hasConnection(insect)) {
            Skeleton.logReturn(this, "placeInsect");
        } else {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insect.setTecton(this);
            insect.neutralizeSporeEffects();
            Skeleton.logReturn(this, "placeInsect");
        }

    }
   
}

