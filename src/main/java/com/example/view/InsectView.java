package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.example.model.Insect;

public class InsectView extends JPanel {
    private final Color color;
    private Position position;
    private float scale = 1.0f;

    public InsectView(Insect insect) {
        this.color = insect.getColor();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int ovalWidth = (int)(position.width * scale);
        int ovalHeight = (int)(position.height * scale);
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, ovalWidth, ovalHeight);
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

}
