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
        setBackground(new Color(0,0,0,0));
        setBounds(0, 0, 1600, 900);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int ovalWidth = (int)(position.width * scale);
        int ovalHeight = (int)(position.height * scale);
        g2d.setColor(color);
        System.out.println("Insect color: " + this.color);
        System.out.println("Insect position: " + this.position.x + " " + this.position.y);
        g2d.fillOval(this.position.x, this.position.y, 30, 30);
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

}
