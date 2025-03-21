package com.example;

public class HypharaSpore extends Spore {

    public HypharaSpore() {
        super(3);
    }

    @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.setSpeed(1.5f);

        Skeleton.logReturn(this, "takeEffectOn");
    }
    
}
