package com.example.model;

/**
 * A Hyphara gombafajhoz tartozó spórákat kezeli.
 */
public class HypharaSpore extends Spore {
    /**
     * A HypharaSpore konstruktora. Beállítja, hogy a spóra tápértéke 3 legyen.
     */
    public HypharaSpore(MushroomBody mushroomBody) {
        super(mushroomBody, 3);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        insect.setSpeed(1.5f);
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
