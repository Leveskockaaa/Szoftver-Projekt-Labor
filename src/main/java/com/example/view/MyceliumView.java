package com.example.view;

import com.example.model.Mycelium;

import javax.swing.*;
import java.awt.*;

public class MyceliumView implements Drawable {
    private Mycelium mycelium;
    private Color color;

    public MyceliumView(Mycelium mycelium) {
        this.mycelium = mycelium;
        switch (mycelium.getMycologist().printType()) {
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
    public void draw( float scale, Graphics2D g2d) {
        // Implement the drawing logic for the mycelium here
        // For example, you might use a graphics library to draw the mycelium shape
        // at the specified position with the given width, height, and rotation.
        ;
    }
}
