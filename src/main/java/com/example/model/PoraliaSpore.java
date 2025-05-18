package com.example.model;

import com.example.Controller;
import com.example.Timer;

/**
 * A Poralia gombafajhoz tartozó spórákat kezeli.
 */
public class PoraliaSpore extends Spore {
    /**
     * A PoraliaSpore konstruktora. Beállítja, hogy a spóra tápértéke 5 legyen.
     */
    public PoraliaSpore(MushroomBody mushroomBody) {
        super(mushroomBody ,5);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        insect.paralize();
        Timer timer = new Timer(10, insect::unParalized);
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
