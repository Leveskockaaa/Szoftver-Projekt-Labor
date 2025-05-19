package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.example.model.Insect;

public class InsectView extends JPanel {
    private Insect insect;
    private Color color;
    private Position position;
    private float scale = 1.0f;
    private boolean isHighlighted = false;

    public InsectView(Insect insect) {
        this.insect = insect;
        this.color = insect.getColor();
        setBackground(new Color(0,0,0,0));
        setBounds(0, 0, 1600, 900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        color = insect.getColor();
        g2d.setColor(color);
        g2d.fillOval(this.position.x, this.position.y, Math.round(15 * scale), Math.round(30 * scale));
        if (isHighlighted) {
            g2d.setColor(Color.RED);
            g2d.drawOval(this.position.x, this.position.y, Math.round(15 * scale), Math.round(30 * scale));
        }
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

}
