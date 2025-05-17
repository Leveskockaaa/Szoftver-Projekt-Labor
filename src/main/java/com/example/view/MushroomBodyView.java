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
        System.out.println("MushroomBodyView constructor called");
        this.mushroomBody = mushroomBody;

        switch (mushroomBody.getClass().getSimpleName()) {
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
       setBackground(new Color(0,0,0,0));
       setBounds(0, 0, 1600, 900);
    }
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("MushroomBodyView paintComponent called");
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int diameter = (int)(30 * scale);
        System.out.println("MushroomBodyView color: " + color);
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, diameter, diameter);
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    private Color pickColor(String type) {
        switch (type) {
            case "Hyphara":
                return new Color(Color.RED.getRGB());
            case "Gilledon":
                return new Color(Color.GREEN.getRGB());
            case "Poralia":
                return new Color(Color.BLUE.getRGB());
            case "Capulon":
                return new Color(Color.PINK.getRGB());
            default:
                return Color.BLACK;
        }
    }
}
