package com.example;

public class PoraliaSpore extends Spore {

    
    protected PoraliaSpore() {
            super(5);
            
        }
    
        @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.paralize();
        
        Skeleton.logReturn(this, "takeEffectOn");
    }
    
}
