package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.example.model.MushroomBody;

public class MushroomBodyView extends JPanel {
    private MushroomBody mushroomBody;
    private Color color;
    private Position position;
    private float scale = 1.0f;

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
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int diameter = (int)(Math.min(position.width, position.height) * scale);
    
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, diameter, diameter);
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }
}
