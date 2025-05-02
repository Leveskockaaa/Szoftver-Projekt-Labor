package com.example.view;

import com.example.model.GameTable;
import com.example.model.Tecton;

import java.util.List;

public class GameTableView {
    GameTable gameTable;

    List<TectonView> tectonViews;

    public GameTableView(GameTable gameTable) {
        for (Tecton tecton : gameTable.getTectons()) {
            TectonView tectonView = new TectonView(tecton);
            tectonViews.add(tectonView);
        }
    }

    public void draw() {
        ;
    }
}
