package com.example;

import java.util.List;

/**
 * A játéktér/pálya irányításáért felel. Ő inicializálja a játék kezdő állapotát, indítja el a játékot és
 * hirdeti ki a győzteseket. A játékelemek kezelését végzi (Tecton, Player).
 */
public class GameTable {

    /**
     * Tecton osztállyal való kapcsolat. A kapcsolat célja,
     * hogy a GameTable kezelni tudja a játéktéren elhelyezkedő tektonokat, amiket egy
     * listában tart nyílván az attribútumai között. Ha a játéktér megsemmisül, akkor a
     * tektonok is. Legalább 10 tekton tartozik a GameTable-hez (kezdő állapot), de ezek
     * száma nőhet.
     */
    private List<Tecton> tectons;

    /**
     * Beállítja a tektonok listáját.
     *
     * @param tectons A tektonok listája, amelyet be kell állítani.
     */
    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }

    /**
     * Inicializálja a játéktáblát. A tektonok listáján végighaladva
     * beállítja a szomszédos tektonokat.
     */
    public void initialize() {
        Skeleton.logFunctionCall(this, "initialize");

        for (int i = 0; i < tectons.size() - 1; i++) {
            tectons.get(i).addTectonToNeighbors(tectons.get(i + 1));
        }
        tectons.getLast().addTectonToNeighbors(tectons.getFirst());
        Skeleton.logReturn(this, "initialize");
    }
    
}
