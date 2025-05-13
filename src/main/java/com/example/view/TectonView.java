package com.example.view;

import com.example.model.Tecton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;

class DividedCircles extends JPanel {
    private static final int CIRCLE_DIAMETER = 100;
    private int x;
    private int y;

    public DividedCircles(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



            // Draw the circle outline
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

            // Draw the three sectors
            for (int j = 0; j < 3; j++) {
                g2d.setColor(Color.BLACK);
                Arc2D sector = new Arc2D.Double(
                        x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER,
                        j * 120, 120, Arc2D.PIE);
                g2d.draw(sector);
            }

            // Draw the circle outline again to cover sector edges
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

    }
}

public class TectonView  implements Drawable {
    private Tecton tecton;
    private Color color;
    private float scale = 1.0f;
    private double radius = 50.0;
    private MushroomBodyView mushroomBodyView;
    private List<InsectView> insectViews = new ArrayList<>();
    private List<MyceliumView> myceliumViews = new ArrayList<>();
    private HashMap<TectonView, Boolean> neighbors = new HashMap<>();
    private SporesView sporeViews;
    private Position position;



    public TectonView(Tecton tecton) {
        this.tecton = tecton;
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
                radius = 50.0;
                break;
            case MEDIUM:
                radius = 100.0;
                break;
            case BIG:
                radius = 150.0;
                break;
            case GIANT:
                radius = 200.0;
                break;
            default:
                radius = 50.0;
                break;
        }
        //mushroomBodyView = new MushroomBodyView(tecton.getMushroomBody());
    }

    @Override
    public void draw(Position position, float scale, JFrame frame) {
        frame.add(new DividedCircles(position.x, position.y));
    }

    public void showSpores() {
        // TODO: Implement the logic to show spores
    }
    public void hideSpores() {
        // TODO: Implement the logic to hide spores
    }
}
