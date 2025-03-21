package com.example;

public class Entomologist extends Player {

    private int score;

    public Entomologist(String name) {
        super(name);
        this.score = 0;
    }

    @Override
    public void placeInitial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeInitial'");
    }

    public void setScore(int score) {
        Skeleton.logFunctionCall(this, "setScore", Integer.toString(score));
        this.score += score;
        Skeleton.logReturn(this, "setScore");
    }
    
}
