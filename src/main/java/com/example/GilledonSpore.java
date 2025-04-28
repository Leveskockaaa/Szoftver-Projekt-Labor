package com.example;

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
        Insect newInsect = new Insect(insect.getEntomologist(), insect.getName()+'b');
        insect.getEntomologist().addInsect(newInsect);

        insect.getTecton().placeInsect(newInsect);
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
