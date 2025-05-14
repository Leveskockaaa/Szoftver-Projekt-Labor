package com.example.view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.model.MushroomBody;

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
        // Compute the circle's diameter based on position dimensions and scale.
        int diameter = (int)(Math.min(position.width, position.height) * scale);
        // Assume position.x and position.y as circle top-left corner.
        // Obtain the Graphics context 
        Graphics2D g2d = (Graphics2D) FungoriumCanvas.getGraphics();
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, diameter, diameter);
    }
}
