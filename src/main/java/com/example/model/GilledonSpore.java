package com.example.model;

import com.example.Controller;
import com.example.Timer;

/**
 * A Gilledon gombafajhoz tartozó spórákat kezeli.
 */
public class GilledonSpore extends Spore {
    /**
     * GilledonSpore konstruktora. Beállítja, hogy a spóra tápértéke 6 legyen.
     */
    public GilledonSpore(MushroomBody mushroomBody) {
        super(mushroomBody, 6);
    }

    /**
     * Kifejti a spóra specifikus hatását a rovaron.
     * @param insect A rovar amire hatni kell.
     */
    @Override
    public void takeEffectOn(Insect insect) {
        insect.setSpeed(.66f);
        Insect newInsect = new Insect(insect.getEntomologist());
        insect.getEntomologist().addInsect(newInsect);
        insect.getTecton().placeInsect(newInsect);

        Timer timer = new Timer(20, () -> {
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
