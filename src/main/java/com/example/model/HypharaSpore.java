package com.example.model;

import com.example.Controller;
import com.example.Timer;

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
        Timer timer = new Timer(30, () -> {
            insect.setSpeed(1.0f);
        });
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
