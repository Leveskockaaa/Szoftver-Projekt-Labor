package com.example.model;

import com.example.Controller;
import com.example.Timer;

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
        Timer timer = new Timer(20, insect::enableToChewMycelium);
        Controller.addTimer(timer);
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
