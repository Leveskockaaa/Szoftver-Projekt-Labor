package com.example;

/**
 * A Hyphara gombafajhoz tartozó spórákat kezeli.
 */
public class HypharaSpore extends Spore {
    /**
     * A HypharaSpore konstruktora. Beállítja, hogy a spóra tápértéke 3 legyen.
     */
    public HypharaSpore() {
        super(3);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.setSpeed(1.5f);

        Skeleton.logReturn(this, "takeEffectOn");
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */
    @Override
    public String printType() {
        return this.getClass().getSimpleName();
    }
}
