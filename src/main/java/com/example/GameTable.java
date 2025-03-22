package com.example;

import java.util.List;

public class GameTable {

    private List<Tecton> tectons;

    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }

    public void initialize() {
        Skeleton.logFunctionCall(this, "initialize");

        for (int i = 0; i < tectons.size() - 1; i++) {
            tectons.get(i).addTectonToNeighbors(tectons.get(i + 1));
        }
        tectons.getLast().addTectonToNeighbors(tectons.getFirst());
        Skeleton.logReturn(this, "initialize");
    }
    
}
