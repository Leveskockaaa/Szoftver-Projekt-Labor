package com.example.view;

import com.example.model.Tecton;

import java.awt.Color;
import java.util.*;

public class TectonView  implements ViewClass {
    Tecton tecton;
    Color color;
    MushroomBodyView mushroomBodyView;
    List<InsectView> insectViews = new ArrayList<>();
    List<MyceliumView> myceliumViews = new ArrayList<>();


    public TectonView(Tecton tecton) {
        this.tecton = tecton;
        this.color = Color.RED; // Default color
    }

    @Override
    public void draw(Position position) {

    }
}
