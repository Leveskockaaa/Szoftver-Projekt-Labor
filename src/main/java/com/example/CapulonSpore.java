package com.example;

public class CapulonSpore extends Spore {

    protected CapulonSpore() {
            super(6);
            
        }
    
        @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.disableChewMycelium();
        
        Skeleton.logReturn(this, "takeEffectOn");
    }
    
}
