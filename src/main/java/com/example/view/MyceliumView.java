package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.example.model.Mycelium;

public class MyceliumView extends JPanel {
    private Mycelium mycelium;
    private Color color;
    private Position position;
    private float scale = 1.0f;

    public MyceliumView(Mycelium mycelium) {
        this.mycelium = mycelium;
       
        switch (mycelium.getMycologist().getType()) {
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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRect(this.position.x, this.position.y, Math.round(5 * scale), Math.round(30 * scale));
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }
}
