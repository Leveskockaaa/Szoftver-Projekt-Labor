package com.example.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.example.model.Mycelium;

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
    public void draw(Position position, float scale) {
        Graphics2D g2d = (Graphics2D) FungoriumCanvas.getGraphics();
        // Compute elongated rectangle dimensions (width is twice the height)
        int rectWidth = (int)(position.width * scale * 2);
        int rectHeight = (int)(position.height * scale);
        // Save original transform
        AffineTransform original = g2d.getTransform();
        // Apply rotation around rectangle center if needed
        if (position.rotation != 0) {
            int centerX = position.x + rectWidth / 2;
            int centerY = position.y + rectHeight / 2;
            g2d.translate(centerX, centerY);
            g2d.rotate(Math.toRadians(position.rotation));
            g2d.translate(-centerX, -centerY);
        }
        g2d.setColor(color);
        g2d.fillRect(position.x, position.y, rectWidth, rectHeight);
        // Restore original transform
        g2d.setTransform(original);
    }
}
