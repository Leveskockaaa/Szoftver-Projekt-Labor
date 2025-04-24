package com.example;

import java.util.List;

/**
 * A játéktér/pálya irányításáért felel. Ő inicializálja a játék kezdő állapotát, indítja el a játékot és
 * hirdeti ki a győzteseket. A játékelemek kezelését végzi (Tecton, Player).
 */
public class GameTable {

    private String name;

    private List<Player> players;

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
        tectons.get(tectons.size() - 1).addTectonToNeighbors(tectons.get(0));
        Skeleton.logReturn(this, "initialize");
    }

    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printName() {
        return this.name;
    }

    public String printTectons() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Tecton t : tectons) {
            sb.append(t.printName()).append(": ").append(t.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    public String printPlayers() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Player p : players) {
            sb.append(p.printName()).append(": ").append(p.printType()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
