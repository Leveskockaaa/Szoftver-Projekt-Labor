package com.example;

public class GilledonSpore extends Spore {

    protected GilledonSpore() {
            super(6);
           
        }
    
        @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.setSpeed(0.66f);
        
        Skeleton.logReturn(this, "takeEffectOn");
    }
    
}
