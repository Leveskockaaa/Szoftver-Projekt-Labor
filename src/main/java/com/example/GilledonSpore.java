package com.example;

/**
 * A Gilledon gombafajhoz tartozó spórákat kezeli.
 */
public class GilledonSpore extends Spore {
    /**
     * GilledonSpore konstruktora. Beállítja, hogy a spóra tápértéke 6 legyen.
     */
    public GilledonSpore() {
        super(6);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        Skeleton.logFunctionCall(this, "takeEffectOn", insect);
        
        insect.setSpeed(0.66f);
        
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
