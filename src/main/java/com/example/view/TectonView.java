package com.example.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

import com.example.model.Capulon;
import com.example.model.Gilledon;
import com.example.model.Hyphara;
import com.example.model.MushroomBody;
import com.example.model.Poralia;
import com.example.model.Tecton;

public class TectonView extends JPanel {
    private Tecton tecton;
    private Color color;
    private float scale = 1.0f;
    private int radius;
    private HashMap<TectonView, Boolean> neighbors = new HashMap<>();
    private SporesView sporeViews;
    private Position position;
    private boolean isHighlighted = false;


    public TectonView(Tecton tecton) {
        this.tecton = tecton;
        setBackground(new Color(0,0,0,0));
        setBounds(0, 0, 1600, 900);
        // System.out.println(this.getHeight() + " " + this.getWidth());
        switch (tecton.printType()) {
            case "Transix":
                color = new Color(0xFE9C9D);
                break;
            case "Magmox":
                color = new Color(0xBFFE9D);
                break;
            case "Mantleon":
                color = new Color(0x9EECFF);
                break;
            case "Orogenix":
                color = new Color(0xDF9DFE);
                break;
        }
        switch (tecton.getSize()){
            case SMALL:
                radius = 20;
                break;
            case MEDIUM:
                radius = 40;
                break;
            case BIG:
                radius = 60;
                break;
            case GIANT:
                radius = 100;
                break;
        }
        this.sporeViews = new SporesView(tecton.getSpores());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw the circle outline
        if (isHighlighted) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.drawOval(position.x, position.y, radius, radius);
        g2d.setColor(color);
        g2d.fillOval(position.x, position.y, radius, radius);

        // Calculate the center of the circle
        int circleCenterX = position.x + radius / 2;
        int circleCenterY = position.y + radius / 2;

        // Draw the radial lines from the specified point to the circle's edge
        g2d.setColor(Color.BLACK);
        for (int angle = 30; angle < 390; angle += 120) {
            double radian = Math.toRadians(angle);
            int endX = (int) (circleCenterX + (radius / 2) * Math.cos(radian));
            int endY = (int) (circleCenterY + (radius / 2) * Math.sin(radian));
            g2d.drawLine(circleCenterX, circleCenterY, endX, endY);
        }

        // Draw the circle outline again to cover sector edges
        if (isHighlighted) {
            g2d.setColor(Color.YELLOW);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.drawOval(position.x, position.y, radius, radius);

        Color mushroomBodyColor;
        MushroomBody mushroomBody = tecton.getMushroomBody();
        if (mushroomBody instanceof Hyphara) {
            mushroomBodyColor = new Color(0xFF0000);
        } else if (mushroomBody instanceof Gilledon) {
            mushroomBodyColor = new Color(0x00FF00);
        } else if (mushroomBody instanceof Poralia) {
            mushroomBodyColor = new Color(0x0000FF);
        } else if (mushroomBody instanceof Capulon) {
            mushroomBodyColor = new Color(0xFF00FF);
        } else {
            mushroomBodyColor = new Color(0x000000);
        }
        g2d.setColor(mushroomBodyColor);
        if (mushroomBody != null) {
            //g2d.fillOval(position.x + radius / 4, position.y + radius / 4, radius / 5, radius / 5);
        }
    }

    public void showSpores() {
        this.sporeViews = new SporesView(tecton.getSpores());
    }
    public void hideSpores() {
        this.sporeViews = null;
    }

    public SporesView getSporeViews() {
        return this.sporeViews;
    }

    public boolean isSelected(int x, int y) {
        int tectonOffsetX = switch (tecton.getSize()) {
            case SMALL -> 50;
            case MEDIUM -> 100;
            case BIG -> 150;
            case GIANT -> 200;
        };
        int tectonOffsetY = switch (tecton.getSize()){
            case SMALL -> 50;
            case MEDIUM -> 100;
            case BIG -> 150;
            case GIANT -> 200;
        };
        return x >= this.position.x && x <= this.position.x + tectonOffsetX &&
                y >= this.position.y && y <= this.position.y + tectonOffsetY;
    }

    public Tecton getTecton() {
        return tecton;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        repaint();
    }
}
