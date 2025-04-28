package com.example;

/**
 * A Capulon gombafajhoz tartozó spórákat kezeli.
 */
public class CapulonSpore extends Spore {
    /**
     * A CalulonSpore konstruktora. Beállítja, hogy a spóra tápértéke 6 legyen.
     */
    public CapulonSpore(MushroomBody mushroomBody) {
        super(mushroomBody, 6);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        insect.disableChewMycelium();
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
