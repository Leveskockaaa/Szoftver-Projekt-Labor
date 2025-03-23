package com.example;

/**
 * A Poralia gombafajhoz tartozó spórákat kezeli.
 */
public class PoraliaSpore extends Spore {
    /**
     * A PoraliaSpore konstruktora. Beállítja, hogy a spóra tápértéke 5 legyen.
     */
    protected PoraliaSpore() {
        super(5);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.paralize();
        
        Skeleton.logReturn(this, "takeEffectOn");
    }
    
}
