package com.example;

import java.util.ArrayList;
import java.util.List;

public class Mycologist extends Player{
    private List<MushroomBody> mushroomBodies = new ArrayList<MushroomBody>();;
    private List<MushroomBody> deadMushroomBodies;
    private List<Mycelium> myceliums;


    Mycologist(String name) {
        super(name);
        mushroomBodies.add(new Hyphara(new Transix()));
    }

    public List<MushroomBody> getMushroomBodies() {
        return mushroomBodies;
    }

    public void collect(MushroomBody mb){
        Skeleton.logFunctionCall(this, "collect", mb);

        Skeleton.logReturn(this, "collect");
    }

    public void removeMycelium(Mycelium my) {
        Skeleton.logFunctionCall(this, "removeMycelium", my);

        Skeleton.logReturn(this, "removeMycelium");
    }

    public void setScore(int score){
        Skeleton.logFunctionCall(this, "setScore", score);

        Skeleton.logReturn(this, "setScore");
    }

    public void placeInitial(Tecton on){
        Skeleton.logFunctionCall(this, "placeInitial", on);

        Skeleton.logReturn(this, "placeInitial");
    }
}
