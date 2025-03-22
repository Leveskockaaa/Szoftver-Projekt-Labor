package com.example;

public abstract class Player {
    protected String name;
    protected int score;
    protected boolean isWinner;

    protected Player(String name) {
        this.name = name;
        this.score = 0;
        this.isWinner = false;
    }

    public void setAsWinner() {
        this.isWinner = true;
    }

    public abstract void placeInitial(Tecton on);

}
