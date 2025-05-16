package com.example.view;

import com.example.model.Tecton;

import javax.swing.*;
import java.awt.Color;
import java.util.*;

public class TectonView extends JPanel {
    private Tecton tecton;
    private Color color;
    private float scale = 1.0f;
    private MushroomBodyView mushroomBodyView;
    private List<InsectView> insectViews = new ArrayList<>();
    private List<MyceliumView> myceliumViews = new ArrayList<>();
    private HashMap<TectonView, Boolean> neighbors = new HashMap<>();
    private SporesView sporeViews;
    private Position position;



    public TectonView(Tecton tecton) {
        this.tecton = tecton;
        switch (tecton.printType()) {
            case "Transix":
                color = new Color(0xFE9C9D);
                break;
            case "Magmox":
                color = new Color(0xBFFE9D);
                break;
            case "Mantleon":
                color = new Color(0x9EECFF);
                break;
            case "Orogenix":
                color = new Color(0xDF9DFE);
                break;
        }
        //mushroomBodyView = new MushroomBodyView(tecton.getMushroomBody());
    }
//    @Override
//    public void draw(Position position, float scale) {
//
//    }

    public void showSpores() {
        // TODO: Implement the logic to show spores
    }
    public void hideSpores() {
        // TODO: Implement the logic to hide spores
    }
}
