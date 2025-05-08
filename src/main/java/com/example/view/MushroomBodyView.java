package com.example.view;

import com.example.model.MushroomBody;

import java.awt.*;

public class MushroomBodyView implements Drawable {
    private MushroomBody mushroomBody;
    private Color color;

    public MushroomBodyView(MushroomBody mushroomBody) {
        this.mushroomBody = mushroomBody;
        switch (mushroomBody.getMycologist().printType()) {
            case "Hyphara":
                color = new Color(Color.RED.getRGB());
                break;
            case "Gilledon":
                color = new Color(Color.GREEN.getRGB());
                break;
            case "Poralia":
                color = new Color(Color.BLUE.getRGB());
                break;
            case "Capulon":
                color = new Color(Color.PINK.getRGB());
                break;
        }
    }
    @Override
    public void draw(Position position, float scale) {
        // Implement the drawing logic for the mycelium here
        // For example, you might use a graphics library to draw the mycelium shape
        // at the specified position with the given width, height, and rotation.
        ;
    }
}
