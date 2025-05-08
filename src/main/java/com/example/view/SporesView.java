package com.example.view;

import com.example.model.Spore;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SporesView implements Drawable {
    private List<Spore> sporeList;
    private HashMap<String, Color> colorMap = new HashMap<>();

    public SporesView(List<Spore> sporeList) {
        this.sporeList = sporeList;
        colorMap.put("HypharaSpore", new Color(Color.RED.getRGB()));
        colorMap.put("GilledonSpore", new Color(Color.GREEN.getRGB()));
        colorMap.put("PoraliaSpore", new Color(Color.BLUE.getRGB()));
        colorMap.put("CapulonSpore", new Color(Color.PINK.getRGB()));
    }


    public void draw(Position position, float scale) {
        // Implement the drawing logic for the spore here
        // For example, you might use a graphics library to draw the spore shape
        // at the specified position with the given width, height, and rotation.
        ;
    }
}
