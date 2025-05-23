package com.example.model;

/**
 * A játékosokat összefogó absztrakt ősosztály.
 */
public abstract class Player {
    /**
     * A játékos neve.
     */
    protected String name;

    /**
     * A játékos pontszáma.
     */
    protected int score;

    /**
     * Egy igaz-hamis változó, hogy a játékos győztes-e a csoportján belül.
     */
    protected boolean isWinner;

    /**
     * A Player osztály konstruktora.
     * @param name A játékos neve.
     */
    protected Player() {
        this.score = 0;
        this.isWinner = false;
    }

    /**
     * Az isWinner attribútumot igazra állítja.
     */
    public void setAsWinner() {
        this.isWinner = true;
    }

    /**
     * Elhelyezi a játékos gombatestét vagy rovarát a kezdő tektonra.
     * Absztrakt metódus, az Entomologist és Mycologist osztályok külön-külön
     * implementálják.
     * @param on A kezdő tekton.
     */
    public abstract void placeInitial(Tecton on);

    public int getScore() { return score; }

    public void setScore(int s) { score = s; }

    public void deductScore(int s) {
        if (score - s < 0) {
            score = 0;
        } else {
            score -= s;
        }
    }

    public boolean getIsWinner() { return isWinner; }

    public String getName() { return name; }
    /*
    =============================================================================================
    Teszteléshez kiíró metódusok
    =============================================================================================
     */

    public String printName() {
        return this.name;
    }

    public abstract String printType();

    public String printScore() {
        return Integer.toString(this.score);
    }

    public String printIsWinner() {
        return isWinner ? "Yes" : "No";
    }

}
