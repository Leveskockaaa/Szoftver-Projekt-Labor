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
        this.mycelia.add(mycelium);
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

        if (insect.getTecton() == null){
            insect.setTecton(this);
            this.insect = insect;
            Skeleton.logReturn(this, "placeInsect");
            return;
        }

        if (this.insect != null || !hasConnection(insect)) {
            Skeleton.logReturn(this, "placeInsect");
        } else {
            insect.neutralizeTectonEffects();
            insect.getTecton().removeInsect();
            insect.setTecton(this);
            insect.setNutrientMultiplier(2);
            Skeleton.logReturn(this, "placeInsect");
        }
    }
   
}

