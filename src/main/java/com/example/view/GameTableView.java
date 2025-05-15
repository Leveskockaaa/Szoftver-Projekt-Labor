package com.example.view;

import com.example.model.GameTable;
import com.example.model.Tecton;
import util.LayeredPane;

import java.util.List;

public class GameTableView {
    GameTable gameTable;
    List<TectonView> tectonViews;
    LayeredPane layeredPane;

    public GameTableView(GameTable gameTable) {
        this.gameTable = gameTable;
        this.layeredPane = new LayeredPane();
        for (Tecton tecton : gameTable.getTectons()) {
            TectonView tectonView = tecton.getView();
            layeredPane.add(tectonView);
        }
    }

    public void draw() {
        ;
    }
}
